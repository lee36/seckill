package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.Store;

public interface StoreService {
    public Store getStoreByUserId(Integer id);

    Boolean updateStore(Store stroe);

    Store getStoreById(Integer id);
}
