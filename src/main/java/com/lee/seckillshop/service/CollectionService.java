package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.Collection;

public interface CollectionService {
    public Boolean addCollection(Collection collection);
    public Boolean deleteById(Integer id);
}
