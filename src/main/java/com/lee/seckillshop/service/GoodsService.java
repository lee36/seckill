package com.lee.seckillshop.service;


import com.lee.seckillshop.model.GoodsCatalog;
import com.lee.seckillshop.vo.GoodSolrDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-30
 */
public interface GoodsService {
    public Map<String,Object> showIndex(Pageable pageable) throws Exception;
    public Page<GoodSolrDocument> findAllSolrGoods(Pageable pageable);
    public Page<GoodSolrDocument> findByInfo(String info,String info1,Pageable pageable);
}
