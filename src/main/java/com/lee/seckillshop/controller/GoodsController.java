package com.lee.seckillshop.controller;

import com.lee.seckillshop.mapper.CatalogMapper;
import com.lee.seckillshop.service.GoodsService;
import com.lee.seckillshop.util.ParseSolrDocumentUtil;
import com.lee.seckillshop.vo.GoodSolrDocument;
import com.lee.seckillshop.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping("/showIndex")
    public Object showIndex(@PageableDefault(page = 1,size = 4) Pageable pageable) throws Exception {
        Map<String, Object> map = goodsService.showIndex(pageable);

        if(map==null||map.size()==0){
            return new ResultResponse(5002,"数据异常",null);
        }
        return new ResultResponse(0,"加载成功",map);
    }
    @RequestMapping("/search")
    public Object searchGoods(String info,@PageableDefault(page = 0,size = 5) Pageable pageable) throws Exception {
        Page page=null;
        HashMap<String, Object> maps = new HashMap<>();
        if(StringUtils.isEmpty(info)){
            page=goodsService.findAllSolrGoods(pageable);
        }else{
            page=goodsService.findByInfo(info,info,pageable);
        }
        ParseSolrDocumentUtil.parseHightLight(GoodSolrDocument.class, page);
        List content = page.getContent();
        if(content==null||content.size()==0){
            return new ResultResponse(501,"没有数据加载",null);
        }
        maps.put("content",content);
        maps.put("totalPage",page.getTotalPages());
        maps.put("page",page.getNumber());
        maps.put("size",page.getSize());
        return new ResultResponse(0,"请求成功",maps);
    }

}
