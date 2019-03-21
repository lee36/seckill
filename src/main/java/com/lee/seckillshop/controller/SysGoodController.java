package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.model.Banner;
import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys")
public class SysGoodController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/getGoods")
    public Object get(Integer id){
        Goods goods = goodsService.findById(id);
        if(goods!=null){
            return new ResultResponse(200, "success", goods);
        }
        return new ResultResponse(500, "error", null);
    }
    @GetMapping("/goodsList")
    public Object goodsList(@PageableDefault(page = 1, size = 2) Pageable pageable,Integer id) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Goods> goodsList = goodsService.getMySelfGoodsList(id);
        if (CollectionUtils.isEmpty(goodsList)) {
            return new ResultResponse(500, "没有任何内容", null);
        }
        return new ResultResponse(200, "success", new PageInfo<Goods>(goodsList));
    }
    @GetMapping("/getMyGoods")
    public Object getMyGoods(Integer id) {
        List<Goods> goodsList = goodsService.getMySelfGoodsList(id);
        if (CollectionUtils.isEmpty(goodsList)) {
            return new ResultResponse(500, "没有任何内容", null);
        }
        return new ResultResponse(200, "success", goodsList);
    }
    @PostMapping("/addGoods")
    public Object addCatalog(MultipartFile file, Goods goods){
        Boolean flag=goodsService.addGood(file,goods);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }

    @PostMapping("/updateGoods")
    public Object updateCatalog(@RequestParam(required = false) MultipartFile file, Goods goods){
        Boolean flag=goodsService.updateGoods(file,goods);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }

    @GetMapping("/delGoods")
    public Object delUser(String goods) {
        String s = goods.substring(1, goods.length() - 1);
        List<Integer> ids = Arrays.asList(s.split(",")).
                stream().map(one -> Integer.parseInt(one)).collect(Collectors.toList());
        Boolean flag = goodsService.deleteSelected(ids);
        if (flag) {
            return new ResultResponse(200, "success", flag);
        }
        return new ResultResponse(500, "error", null);
    }
}
