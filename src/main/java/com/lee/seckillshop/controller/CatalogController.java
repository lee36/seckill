package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.service.CatalogService;
import com.lee.seckillshop.commons.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-11
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @RequestMapping("/all")
    public Object getAllCatalogs() {
        List<GoodsCatalog> allCatalog = catalogService.getAllCatalog();
        if (allCatalog != null) {
            return new ResultResponse(0, "获取成功", allCatalog);
        }
        return new ResultResponse(501, "获取失败", null);
    }
}
