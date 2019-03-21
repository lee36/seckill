package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.model.Collection;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("collection")
public class ColletionController {
     @Autowired
     private CollectionService collectionService;

     @PostMapping("/add")
     public Object addCollection(Collection collection){
         Boolean b = collectionService.addCollection(collection);
         if(b){
             return new ResultResponse(200,"success",b);
         }
         return new ResultResponse(500,"error",b);
     }
    @PostMapping("/delete")
    public Object addCollection(Integer id){
        Boolean b = collectionService.deleteById(id);
        if(b){
            return new ResultResponse(200,"success",b);
        }
        return new ResultResponse(500,"error",b);
    }
}
