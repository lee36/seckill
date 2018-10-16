package com.lee.seckillshop.mapper;

import com.lee.seckillshop.vo.GoodSolrDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2018-09-29
 * 商品的solr操作接口
 */
@Repository
public interface GoodSolrDocumentRepository extends SolrCrudRepository<GoodSolrDocument,String> {
    @Highlight(prefix = "<font color='red'>",postfix = "</font>")
    public Page<GoodSolrDocument> findByGoodInfoOrGoodNameLike(String info,String info1,Pageable pageable);
}