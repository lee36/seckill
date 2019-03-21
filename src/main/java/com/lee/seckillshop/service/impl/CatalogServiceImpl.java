package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.mapper.CatalogMapper;
import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author admin
 * @date 2018-10-11
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    private static String uploadPath="F:\\IDEA_project\\seckill-shop\\target\\classes\\static\\catalog";
    private static String showPath="http://localhost:8080/catalog/";

    @Autowired
    private CatalogMapper catalogMapper;

    @Override
    public List<GoodsCatalog> getAllCatalog() {
        return catalogMapper.findAllCatalog();
    }

    @Override
    @Transactional
    public Boolean addCatalog(MultipartFile file, GoodsCatalog catalog) {
        //生成新的文件名称
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."), filename.length());
        String newFileName=UUID.randomUUID().toString();
        String newFullFileName=newFileName+suffix;
        String dbFileName=showPath+newFullFileName;
        //先存入数据库
        catalog.setIcon(dbFileName);
        int i = catalogMapper.addCatalog(catalog);
        if(i>0){
            //上传文件
            try {
                file.transferTo(new File(uploadPath, newFullFileName));
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteSelected(List<Integer> ids) {
        try {
            ids.stream().forEach((id) -> {
                catalogMapper.deleteById(id);
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public GoodsCatalog getCatalog(Integer id) {
        return catalogMapper.getCatalog(id);
    }

    @Override
    public Boolean updateCatalog(MultipartFile file, GoodsCatalog catalog) {
        String newFullFileName=null;
        if(file!=null){
            //先存入数据库
            //生成新的文件名称
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf("."), filename.length());
            String newFileName=UUID.randomUUID().toString();
            newFullFileName=newFileName+suffix;
            String dbFileName=showPath+newFullFileName;
            catalog.setIcon(dbFileName);
        }
        int i = catalogMapper.updateCatalog(catalog);
        if(i>0){
            //上传文件
            if(file!=null){
                //先存入数据库
                try {
                    file.transferTo(new File(uploadPath, newFullFileName));
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }
}
