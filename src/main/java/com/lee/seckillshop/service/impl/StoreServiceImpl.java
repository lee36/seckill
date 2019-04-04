package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.model.Store;
import com.lee.seckillshop.mapper.StoreMapper;
import com.lee.seckillshop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreMapper storeMapper;
    @Override
    public Store getStoreByUserId(Integer id) {
        return storeMapper.findByUserId(id);
    }

    @Override
    public Boolean updateStore(Store stroe) {
        return storeMapper.updateStore(stroe)==1?true:false;
    }

    @Override
    public Store getStoreById(Integer id) {
        return storeMapper.findById(id);
    }
}
