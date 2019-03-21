package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.GoodsCatalog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-11
 */
public interface CatalogService {
    public List<GoodsCatalog> getAllCatalog();

    public Boolean addCatalog(MultipartFile file, GoodsCatalog catalog);

    Boolean deleteSelected(List<Integer> ids);

    GoodsCatalog getCatalog(Integer id);

    Boolean updateCatalog(MultipartFile file, GoodsCatalog catalog);
}
