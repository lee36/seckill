package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.model.Collection;
import com.lee.seckillshop.mapper.CollectionMapper;
import com.lee.seckillshop.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Override
    public Boolean addCollection(Collection collection) {
        int i = collectionMapper.addCollection(collection);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteById(Integer id) {
        int i = collectionMapper.deleteById(id);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Collection> getMyCollectors(Integer id) {
        return collectionMapper.getMyCollectors(id);
    }
}
