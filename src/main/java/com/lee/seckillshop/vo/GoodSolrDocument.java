package com.lee.seckillshop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author admin
 * @date 2018-09-29
 * 商品对应的SolrDocument
 */
@SolrDocument(solrCoreName = "goods_core")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodSolrDocument {
   @Field("id")
   @Id
   private String id;
   @Field("good_name")
   private String goodName;
   @Field("good_price")
   private Integer goodPrice;
   @Field("good_stock")
   private Integer goodStock;
   @Field("weight")
   private Integer weight;
   @Field("good_info")
   private String goodInfo;
   @Field("good_img")
   private String goodImg;
   @Field("good_store_id")
   private Integer storeId;

}
