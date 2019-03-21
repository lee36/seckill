package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.SeckillGood;

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
}
