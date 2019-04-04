package com.lee.seckillshop.service;


import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.commons.vo.GoodSolrDocument;
import com.lee.seckillshop.commons.vo.SeckillGoodVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-30
 */
public interface GoodsService {
    public Map<String, Object> showIndex(Pageable pageable) throws Exception;

    public Page<GoodSolrDocument> findAllSolrGoods(Pageable pageable);

    public Page<GoodSolrDocument> findByInfo(String info, String info1, Pageable pageable);

    public Goods findById(Integer id);

    public List<Goods> getMySelfGoodsList(Integer id);

    Boolean addGood(MultipartFile file, Goods goods);

    Boolean updateGoods(MultipartFile file, Goods goods);

    Boolean deleteSelected(List<Integer> ids);

    List<Goods> getIndexGoods();

    List<Goods> getCatalogGoods(Integer id);

    List<Goods> getStoreGoods(Integer id);

    public List<SeckillGoodVo> addStartTimeAndFinishedTime(List<SeckillGoodVo> seckillGoods);
}
