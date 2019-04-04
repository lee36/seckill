package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.model.Banner;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.mapper.BannerMapper;
import com.lee.seckillshop.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys")
public class SysBannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/addBanner")
    public Object addBanner(List<MultipartFile> file) {
        Boolean flag = bannerService.addBanners(file);
        if (flag) {
            return new ResultResponse(200, "success", flag);
        }
        return new ResultResponse(500, "error", null);
    }

    @GetMapping("/bannerList")
    public Object bannerList(@PageableDefault(page = 1, size = 2) Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Banner> bannerList = bannerService.getAllBanner();
        if (CollectionUtils.isEmpty(bannerList)) {
            return new ResultResponse(500, "没有任何内容", null);
        }
        return new ResultResponse(200, "success", new PageInfo<Banner>(bannerList));
    }

    @GetMapping("/delBanners")
    public Object delUser(String banners) {
        String s = banners.substring(1, banners.length() - 1);
        List<Integer> ids = Arrays.asList(s.split(",")).
                stream().map(one -> Integer.parseInt(one)).collect(Collectors.toList());
        Boolean flag = bannerService.deleteSelected(ids);
        if (flag) {
            return new ResultResponse(200, "success", flag);
        }
        return new ResultResponse(500, "error", null);
    }

    @GetMapping("/cageBanner")
    public Object cageUser(String ids, int flag) {
        String s = ids.substring(1, ids.length() - 1);
        List<Integer> nums = Arrays.asList(s.split(",")).
                stream().map(one -> Integer.parseInt(one)).collect(Collectors.toList());
        Boolean b = bannerService.cageBanner(nums, flag);
        if (b) {
            return new ResultResponse(200, "success", null);
        }
        return new ResultResponse(500, "error", null);
    }
}