package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.model.Collection;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("collection")
public class ColletionController {
     @Autowired
     private CollectionService collectionService;

     @PostMapping("/add")
     public Object addCollection(Collection collection){
         System.out.println(collection);
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
    @GetMapping("/getMyCollectors")
    public Object getMyCollectors(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int size, Integer id){
        PageHelper.startPage(page,size);
        List<Collection> collectionList=collectionService.getMyCollectors(id);
        PageInfo<Collection> pageInfo = new PageInfo<Collection>(collectionList);
        if(!CollectionUtils.isEmpty(collectionList)){
            return new ResultResponse(200,"success",pageInfo);
        }
        return new ResultResponse(500,"error",null);
    }
}
