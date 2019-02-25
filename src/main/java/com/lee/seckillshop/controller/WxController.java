package com.lee.seckillshop.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lee.seckillshop.exception.PayUnkownException;
import com.lee.seckillshop.mapper.OrderMapper;
import com.lee.seckillshop.model.User;
import com.lee.seckillshop.properties.WxLoginProperties;
import com.lee.seckillshop.properties.WxPayProperties;
import com.lee.seckillshop.service.OrderService;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.util.*;
import com.lee.seckillshop.vo.ResultResponse;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author admin
 * @date 2018-09-20
 */
@Controller
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private WxLoginProperties wxLoginProperties;
    @Autowired
    private WxPayProperties wxPayProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/login_url")
    @ResponseBody
    public Object getWxLoginUrl(@RequestParam("access_page") String access_page) {
        String loginUrl = wxLoginProperties.getLoginUrl();
        String realLoginUrl = String.format(loginUrl, wxLoginProperties.getAppId(),
                wxLoginProperties.getRedictUrl(), access_page);
        System.out.println(realLoginUrl + "====");
        return realLoginUrl;
    }

    @RequestMapping("/login/back")
    public Object LoginBack(@RequestParam("code") String code,
                            @RequestParam("state") String state) throws Exception {
        String accessTokenUrl = wxLoginProperties.getAccessTokenUrl();
        String realAccessTokenUrl = String.format(accessTokenUrl, wxLoginProperties.getAppId(),
                wxLoginProperties.getAppSecret(), code);
        String str = restTemplate.getForObject(realAccessTokenUrl, String.class);
        Map map = JsonUtil.json2Obj(str, Map.class);
        String access_token = (String) map.get("access_token");
        String openId = (String) map.get("openid");
        //通过openId判断是否用户存在
        Map userMap = userService.inferUserExits(openId, access_token);
        return "redirect:http://10.1.0.88:8080/" + state + "?token=" + userMap.get("token");
    }

    @RequestMapping(value = "/place/order", method = RequestMethod.GET)
    public void PlaceAnOrder(@RequestParam("flag") Integer flag, @RequestParam("goodIds") List<Integer> goodIds,
                             @RequestParam("nums") List<Integer> nums, @RequestParam("money") Integer money, @RequestParam("userId") Integer userId
            , HttpServletRequest request, HttpServletResponse response) {
        //获取主机ip
        String ip = IpUtils.getIpAddr(request);
        //可以排序的map
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", wxPayProperties.getAppId());
        map.put("mch_id", wxPayProperties.getMerId());
        map.put("nonce_str", CommonUtil.generateUUID());
        map.put("body", "seckill-shop item create by lee");
        //订单号
        String orderId = CommonUtil.generateUUID();
        map.put("out_trade_no", orderId);
        //计算总金额
        map.put("total_fee", money + "");
        map.put("spbill_create_ip", ip);
        map.put("notify_url", wxPayProperties.getBackUrl());
        map.put("trade_type", "NATIVE");
        //sign签名
        String sign = WxPayUtil.createSign(map, wxPayProperties.getKey());
        map.put("sign", sign);
        //将map对象转换成xml字符串
        try {
            String payXml = WxPayUtil.mapToXml(map);
            String result = HttpUtils.doPost(wxPayProperties.getPayUrl(), payXml, 2000);
            result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            if (result == null) {
                throw new PayUnkownException("支付未知异常");
            }
            Map<String, String> orderMap = WxPayUtil.xmlToMap(result);
            String code_url = orderMap.get("code_url");
            System.out.println(code_url + "========");
            //支付预处理
            orderService.createSeckillOrder(flag, goodIds, orderId, userId, money, nums);
            //生成二维码
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, 400, 400, hints);
            ServletOutputStream outputStream = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (Exception e) {
            throw new PayUnkownException("支付未知异常");
        }
    }

    @RequestMapping("/pay/back")
    public Object back(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletInputStream in = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        in.close();
        Map<String, String> result = WxPayUtil.xmlToMap(sb.toString());
        //获取sortedMap
        SortedMap<String, String> map = WxPayUtil.getSortedMap(result);
        //判断签名是否正确
        boolean flag = WxPayUtil.isCorrectSign(map, wxPayProperties.getKey());
        if (flag) {
            //业务场景
            if (map.get("result_code").equals("SUCCESS")) {
                //获取订单号
                String out_trade_no = map.get("out_trade_no");
                //先从订单表中查询
                try {
                    orderService.updateState(out_trade_no);
                } catch (Exception e) {
                    throw new PayUnkownException("支付异常");
                }
                return new ResultResponse(0, "支付成功", null);
            }
        }
        //支付失败
        return new ResultResponse(600, "支付未知异常", null);
    }
}
