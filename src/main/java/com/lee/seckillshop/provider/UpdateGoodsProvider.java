package com.lee.seckillshop.provider;

import com.lee.seckillshop.commons.model.Goods;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class UpdateGoodsProvider {
    public String update(Goods goods){
        return new SQL(){
            {
                UPDATE("goods_tb");
                if(!StringUtils.isEmpty(goods.getName())){
                    SET("name=#{name}");
                }
                if(!StringUtils.isEmpty(goods.getPrice())){
                    SET("price=#{price}");
                }
                if(!StringUtils.isEmpty(goods.getStatus())){
                    SET("status=#{status}");
                }
                if(!StringUtils.isEmpty(goods.getStock())){
                    SET("stock=#{stock}");
                }
                if(!StringUtils.isEmpty(goods.getInfo())){
                    SET("info=#{info}");
                }
                if(!StringUtils.isEmpty(goods.getDetail())){
                    SET("detail=#{detail}");
                }
                if(!StringUtils.isEmpty(goods.getImg())){
                    SET("img=#{img}");
                }
                if(!StringUtils.isEmpty(goods.getWeight())){
                    SET("weight=#{weight}");
                }
                if(goods.getStore()!=null&&goods.getStore().getId()!=null){
                    SET("store_id=#{store.id}");
                }
                if(goods.getGoodsCatalog()!=null&&goods.getGoodsCatalog().getId()!=null){
                    SET("catalog_id=#{goodsCatalog.id}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
