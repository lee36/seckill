package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.model.Banner;
import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.commons.model.SeckillGood;
import com.lee.seckillshop.commons.util.ParseSolrDocumentUtil;
import com.lee.seckillshop.service.BannerService;
import com.lee.seckillshop.service.GoodSeckillService;
import com.lee.seckillshop.service.GoodsService;
import com.lee.seckillshop.commons.vo.GoodSolrDocument;
import com.lee.seckillshop.commons.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-28
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private GoodSeckillService goodSeckillService;

    @RequestMapping("/getBanners")
    public Object getIndexBanners(){
        List<Banner> banners=bannerService.getNewBanner();
        if(banners!=null){
            return new ResultResponse(200, "sccess", banners);
        }
        return new ResultResponse(500,"error", null);
    }
    @RequestMapping("/getSeckill")
    public Object getIndexSeckill(){
       List<List<SeckillGood>> seckillGoods=goodSeckillService.getIndexSeckill();
        if(seckillGoods!=null){
            return new ResultResponse(200, "sccess", seckillGoods);
        }
        return new ResultResponse(500,"error", null);
    }
    @RequestMapping("/getGoods")
    public Object getGoods(){
        List<Goods> goods=goodsService.getIndexGoods();
        if(goods!=null){
            return new ResultResponse(200, "sccess", goods);
        }
        return new ResultResponse(500,"error", null);
    }
    @RequestMapping("/showIndex")
    public Object showIndex(@PageableDefault(page = 1, size = 4) Pageable pageable) throws Exception {
        Map<String, Object> map = goodsService.showIndex(pageable);

        if (map == null || map.size() == 0) {
            return new ResultResponse(5002, "数据异常", null);
        }
        return new ResultResponse(0, "加载成功", map);
    }

    @RequestMapping("/search")
    public Object searchGoods(String info, @PageableDefault(page = 0, size = 5) Pageable pageable) throws Exception {
        Page page = null;
        HashMap<String, Object> maps = new HashMap<>();
        if (StringUtils.isEmpty(info)) {
            page = goodsService.findAllSolrGoods(pageable);
        } else {
            page = goodsService.findByInfo(info, info, pageable);
        }
        ParseSolrDocumentUtil.parseHightLight(GoodSolrDocument.class, page);
        List content = page.getContent();
        if (content == null || content.size() == 0) {
            return new ResultResponse(501, "没有数据加载", null);
        }
        maps.put("content", content);
        maps.put("totalPage", page.getTotalPages());
        maps.put("page", page.getNumber());
        maps.put("size", page.getSize());
        return new ResultResponse(0, "请求成功", maps);
    }

    @GetMapping("/details")
    public Object getDetail(Integer id){
        Goods good = goodsService.findById(id);
        if(good!=null){
            return new ResultResponse(200,"success",good);
        }
        return new ResultResponse(500,"error",null);
    }
    @RequestMapping("/getCatalogGoods")
    public Object getCatalogGoods(Integer id){
        List<Goods> lists=goodsService.getCatalogGoods(id);
        if(lists!=null&&lists.size()!=0){
            return new ResultResponse(200,"success",lists);
        }
        return new ResultResponse(500,"error",null);
    }
    @RequestMapping("/getStoreGoods")
    public Object getStoreGoods(Integer id){
        List<Goods> goods=goodsService.getStoreGoods(id);
        if(goods!=null&&goods.size()!=0){
            return new ResultResponse(200,"success",goods);
        }
        return new ResultResponse(500,"error",null);
    }
}
