package com.lee.seckillshop.provider;

import com.lee.seckillshop.commons.model.GoodsCatalog;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class CatalogProvider {
    public String update(GoodsCatalog catalog){
        return new SQL(){
            {
              UPDATE("goods_catalog_tb");
              if(!StringUtils.isEmpty(catalog.getName())){
                  SET("name=#{name}");
              }
                if(!StringUtils.isEmpty(catalog.getIcon())){
                    SET("icon=#{icon}");
                }
                if(!StringUtils.isEmpty(catalog.getInfo())){
                    SET("info=#{info}");
                }
                WHERE("id=#{id}");
            }
        }.toString();

    }
}
