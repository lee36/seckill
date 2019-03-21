package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;
import com.lee.seckillshop.commons.model.Advice;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.AdviceService;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys")
public class SysAdviceController {
    private static String uploadPath="F:\\IDEA_project\\seckill-shop\\target\\classes\\static\\advice";
    private static String showPath="http://localhost:8080/advice/";
    @Autowired
    private AdviceService adviceService;
    @GetMapping("/adviceList")
    public Object userList(@PageableDefault(page = 1,size = 2) Pageable pageable){
        PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
        List<Map<String,Object>> adviceList = adviceService.getAllAdviceToApp();
        if(CollectionUtils.isEmpty(adviceList)){
            return new ResultResponse(500,"没有任何内容",null);
        }
        return new ResultResponse(200,"success",new PageInfo<Map<String,Object>>(adviceList));
    }
    @GetMapping("/delAdvice")
    public Object delUser(String advices){
        String s = advices.substring(1, advices.length()-1);
        List<Integer> ids = Arrays.asList(s.split(",")).
                stream().map(one->Integer.parseInt(one)).collect(Collectors.toList());
        Boolean flag = adviceService.deleteSelected(ids);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }

    @GetMapping("/getAdvice")
    public Object getAdvice(Integer id){
        Advice advice = adviceService.getAdvice(id);
        if(advice!=null){
            return new ResultResponse(200,"success",advice);
        }
        return new ResultResponse(500,"error",null);
    }

    @PostMapping("/refAdvice/{publish}")
    public Object refAdvice(@PathVariable("publish") Integer publish,
                            @RequestBody Advice advice){
        Boolean flag = adviceService.addComment(publish,advice);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }

    @PostMapping("/upload")
    public Object upload(List<MultipartFile> file){
        List<String> urls = new ArrayList<>();
        try {
            file.stream().forEach((file1) -> {
                try {
                    String fileName = UUID.randomUUID().toString();
                    //获取文件名后缀
                    String suffix = file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."), file1.getOriginalFilename().length());
                    file1.transferTo(new File(uploadPath, fileName+suffix));
                    urls.add(showPath+fileName+suffix);
                } catch (IOException e) {
                    return ;
                }
            });
            return ImmutableMap.of("errno",0,"data",urls);
        }catch (Exception e){
            return ImmutableMap.of("errno",1,"data",urls);
        }
    }
}
