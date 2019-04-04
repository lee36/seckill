package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.SeckillGood;
import com.lee.seckillshop.commons.vo.SeckillGoodVo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author admin
 * @date 2018-10-15
 */
public interface GoodSeckillService {
    public boolean seckillGood(Integer seckGoodId, Integer userId) throws Exception;
    public void createOrder(Integer userId, Integer seckGoodId);
    public Boolean addSeckillGood(SeckillGood good);
    public Boolean updateSeckillGood(Integer status,Integer id);
    List<SeckillGood> getMySelfGoodsList(Integer id);
    public Boolean deleteSelected(List<Integer> ids);

    List<List<SeckillGood>> getIndexSeckill();

    SeckillGoodVo getSeckillById(Integer id);

    public List<LinkedHashMap<String, Object>> getAllSeckillGood();
}
