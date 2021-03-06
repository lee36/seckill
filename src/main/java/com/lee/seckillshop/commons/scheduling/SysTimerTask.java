package com.lee.seckillshop.commons.scheduling;

import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.mapper.FlowMapper;
import com.lee.seckillshop.mapper.GoodSolrDocumentRepository;
import com.lee.seckillshop.mapper.GoodsMapper;
import com.lee.seckillshop.commons.model.Flow;
import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.commons.vo.GoodSolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2018-09-28
 * 系统定时器
 */
@Component
public class SysTimerTask {
    @Autowired
    private FlowMapper flowMapper;
    @Autowired
    private JedisComponet jedisTemplate;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodSolrDocumentRepository goodSolrDocumentRepository;

    /**
     * 指定在凌晨时将redis中的用户访问数据加载到数据库中
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void putUserVisitedIntoMysql() throws Exception {
        System.out.println("==========定时器启动=============");
        Long num = jedisTemplate.get("user:visited", Long.class);
        Flow flow = new Flow();
        flow.setNum(num);
        flowMapper.saveFlow(flow);
        jedisTemplate.delete("user:visited");
        System.out.println("==========定时器完成=============");
    }

    /**
     * 每隔10秒同步数据库与搜索引擎
     */
    @Scheduled(fixedRate = 10000L)
    public void addGoods2SolrDocument() {
        //从数据库中加载所有Goods
        List<Goods> allGoods = goodsMapper.findAllGoods();
        if (allGoods.size() <= 0) {
            return;
        }
        List<GoodSolrDocument> allSolrGoods = new ArrayList<>();
        for (Goods goods : allGoods) {
            GoodSolrDocument goodSolrDocument = new GoodSolrDocument();
            goodSolrDocument.setGoodImg(goods.getImg());
            goodSolrDocument.setGoodInfo(goods.getInfo());
            goodSolrDocument.setGoodName(goods.getName());
            goodSolrDocument.setGoodPrice(goods.getPrice());
            goodSolrDocument.setGoodStock(goods.getStock());
            goodSolrDocument.setId(goods.getId() + "");
            goodSolrDocument.setStoreId(goods.getStore().getId());
            goodSolrDocument.setWeight(goods.getWeight());
            allSolrGoods.add(goodSolrDocument);
        }
        goodSolrDocumentRepository.saveAll(allSolrGoods);
    }
}
