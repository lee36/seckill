package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.Collection;

import java.util.List;

public interface CollectionService {
    public Boolean addCollection(Collection collection);
    public Boolean deleteById(Integer id);

    List<Collection> getMyCollectors(Integer id);
}
