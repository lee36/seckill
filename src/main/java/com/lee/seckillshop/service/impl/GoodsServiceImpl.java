package com.lee.seckillshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.mapper.CatalogMapper;
import com.lee.seckillshop.mapper.GoodSolrDocumentRepository;
import com.lee.seckillshop.mapper.GoodsMapper;
import com.lee.seckillshop.mapper.SeckillGoodsMapper;
import com.lee.seckillshop.model.Goods;
import com.lee.seckillshop.model.GoodsCatalog;
import com.lee.seckillshop.model.SeckillGood;
import com.lee.seckillshop.service.GoodsService;
import com.lee.seckillshop.vo.GoodSolrDocument;
import com.lee.seckillshop.vo.SeckillGoodVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-30
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private GoodSolrDocumentRepository goodSolrDocumentRepository;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public Map<String, Object> showIndex(Pageable pageable) throws Exception {
        Map map = jedisTemplate.get("index:info:page="+pageable.getPageNumber(), Map.class);
        HashMap<String, Object> info = new HashMap<>();
        if(map==null||map.size()==0){
            PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
            List<Goods> popularGoods = goodsMapper.findTop4ByWeight();
            PageInfo<Goods> pageInfo = new PageInfo<>(popularGoods);
            info.put("goods",pageInfo);
            List<SeckillGoodVo> seckillGoods = seckillGoodsMapper.seckillGoodListTop4();
            info.put("seckillGoods",addStartTimeAndFinishedTime(seckillGoods));
            info.put("catalogs",catalogMapper.findAllCatalog());
            jedisTemplate.set("index:info:page="+pageable.getPageNumber(),info,5);
            return info;
        }else {
            return map;
        }
    }

    @Override
    public Page<GoodSolrDocument> findAllSolrGoods(Pageable pageable) {
        return goodSolrDocumentRepository.findAll(pageable);
    }

    @Override
    public Page<GoodSolrDocument> findByInfo(String info,String info1,Pageable pageable) {
        return goodSolrDocumentRepository.findByGoodInfoOrGoodNameLike(info,info1,pageable);
    }

    /**
     * 添加秒杀开始时间和时间
     * @param seckillGoods
     * @return
     */
    private List<SeckillGoodVo> addStartTimeAndFinishedTime(List<SeckillGoodVo> seckillGoods){
        for (SeckillGoodVo seckillGood : seckillGoods) {
            seckillGood.setFinishedTime(seckillGood.getEndTime().getTime());
            seckillGood.setStartTime(seckillGood.getCreateTime().getTime());
        }
        return seckillGoods;
    }
}
