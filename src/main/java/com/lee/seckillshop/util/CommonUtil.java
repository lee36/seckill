package com.lee.seckillshop.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author admin
 * @date 2018-09-20
 */
public class CommonUtil {

    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str(32)
     * @return
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().
                replaceAll("-","").substring(0,32);

        return uuid;
    }


    /**
     * md5常用工具类
     * @param data
     * @return
     */
    public static String MD5(String data){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte [] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toUpperCase();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 日期转换类
     */
    public static String dateFormat(Date date,String pattern){
       return  new SimpleDateFormat(pattern).format(new Date());
    }
    /**
     * 根据字符串生成相应的二维码
     */
    public static void generatQRCode(String code,OutputStream out) throws Exception {
        Map<EncodeHintType,Object> hints=new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(code,BarcodeFormat.QR_CODE,400,400,hints);
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",out);
    }
}
