package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BannerService {

    public Boolean addBanners(List<MultipartFile> files);

    public List<Banner> getAllBanner();

    public Boolean deleteSelected(List<Integer> ids);

    public Boolean cageBanner(List<Integer> nums,Integer flag);
}
