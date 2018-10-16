package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.mapper.CatalogMapper;
import com.lee.seckillshop.model.GoodsCatalog;
import com.lee.seckillshop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-11
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogMapper catalogMapper;
    @Override
    public List<GoodsCatalog> getAllCatalog() {
        return catalogMapper.findAllCatalog();
    }
}
