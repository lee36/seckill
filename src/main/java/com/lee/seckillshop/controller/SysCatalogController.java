package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.model.Banner;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys")
public class SysCatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/catalogList")
    public Object bannerList(@PageableDefault(page = 1, size = 2) Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<GoodsCatalog> catalogList = catalogService.getAllCatalog();
        if (CollectionUtils.isEmpty(catalogList)) {
            return new ResultResponse(500, "没有任何内容", null);
        }
        return new ResultResponse(200, "success", new PageInfo<GoodsCatalog>(catalogList));
    }
    @PostMapping("/addCatalog")
    public Object addCatalog(MultipartFile file,GoodsCatalog catalog){
        Boolean flag=catalogService.addCatalog(file,catalog);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }
    @GetMapping("/delCatalog")
    public Object delUser(String catalogs) {
        String s = catalogs.substring(1, catalogs.length() - 1);
        List<Integer> ids = Arrays.asList(s.split(",")).
                stream().map(one -> Integer.parseInt(one)).collect(Collectors.toList());
        Boolean flag = catalogService.deleteSelected(ids);
        if (flag) {
            return new ResultResponse(200, "success", flag);
        }
        return new ResultResponse(500, "error", null);
    }
    @GetMapping("/getCatalog")
    public Object delUser(Integer id) {
        GoodsCatalog catalog=catalogService.getCatalog(id);
        if (catalog!=null) {
            return new ResultResponse(200, "success", catalog);
        }
        return new ResultResponse(500, "error", null);
    }
    @PostMapping("/updateCatalog")
    public Object updateCatalog(@RequestParam(required = false) MultipartFile file, GoodsCatalog catalog){
        Boolean flag=catalogService.updateCatalog(file,catalog);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }
}
