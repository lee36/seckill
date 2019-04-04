package com.lee.seckillshop.controller;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.lee.seckillshop.commons.model.Store;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.mapper.StoreMapper;
import com.lee.seckillshop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class StoreController {
     @Autowired
     private StoreService storeService;

     @RequestMapping("/get")
     public Object getStore(Integer id){
         Store store = storeService.getStoreByUserId(id);
         if(store!=null){
             return new ResultResponse(200,"success",store);
         }
         return new ResultResponse(500,"error",null);
     }
      @PostMapping("/update")
      public Object updateStore(@RequestBody Store stroe){
         Boolean flag=storeService.updateStore(stroe);
         if(flag){
               return new ResultResponse(200,"success",flag);
         }
          return new ResultResponse(500,"error",null);
      }
    @RequestMapping("/getById")
    public Object getStoreById(Integer id){
        Store store = storeService.getStoreById(id);
        if(store!=null){
            return new ResultResponse(200,"success",store);
        }
        return new ResultResponse(500,"error",null);
    }
}
