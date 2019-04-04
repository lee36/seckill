package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.model.Banner;
import com.lee.seckillshop.form.UserUpdateForm;
import com.lee.seckillshop.mapper.BannerMapper;
import com.lee.seckillshop.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BannerServiceImpl implements BannerService {

    private static String uploadPath="F:\\IDEA_project\\seckill-shop\\src\\main\\resources\\upload\\banners";
    private static String showPath="http://localhost:8080/banners/";

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    @Transactional
    public Boolean addBanners(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            try {
                //生成新的文件名字
                String newName=UUID.randomUUID().toString();
                //获取本身名字
                String oldName=file.getOriginalFilename();
                //获取后缀名字
                String suffix=oldName.substring(oldName.lastIndexOf("."),oldName.length());
                //生成新名称
                String newFullName=newName+suffix;
                file.transferTo(new File(uploadPath,newFullName));
                //插入数据库
                bannerMapper.addBanner(Banner.builder().bannerName(showPath+newFullName).status(1).build());
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Banner> getAllBanner() {
        return bannerMapper.getAllBanners();
    }

    @Override
    public Boolean deleteSelected(List<Integer> ids) {
        try {
            ids.stream().forEach((id) -> {
                bannerMapper.deleteById(id);
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean cageBanner(List<Integer> nums, Integer flag) {
      try {
          if (flag == 0) {
              //禁用
              nums.stream().forEach((num) -> {
                  bannerMapper.cage(num);
              });
          } else {
              //开启
              nums.stream().forEach((num) -> {
                  bannerMapper.realse(num);
              });
          }
          return true;
      }catch (Exception e) {
          return false;
      }
    }

    /**
     * 获取最新的可用的banner
     * @return
     */
    @Override
    public List<Banner> getNewBanner() {
        return bannerMapper.getNewBanners();
    }
}
