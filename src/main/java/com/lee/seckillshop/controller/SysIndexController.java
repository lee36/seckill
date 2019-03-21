package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.model.Flow;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.FlowService;
import com.lee.seckillshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class SysIndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private FlowService flowService;

    @GetMapping("/getuserNums")
    public Object getuserNums(){
        try {
            Integer users = userService.countUsers();
            return new ResultResponse(200,"success",users);
        } catch (Exception e) {
            return new ResultResponse(500,"error",null);
        }
    }

    @GetMapping("/lastSixFlow")
    public Object lastSixFlow(){
        List<Flow> flowList = flowService.lastSixFlow();
        List<String> times = new ArrayList<>();
        List<Long> nums = new ArrayList<>();
        if(flowList.size()>0){
            //装数据
            Map<String, Object> map = flowService.fillDateToMap(times, nums, flowList);
            return new ResultResponse(200,"success",map);
        }
        return new ResultResponse(500,"error",null);
    }
    @GetMapping("/groupByCity")
    public Object groupByCity(){
        List<Map<String, Object>> maps = userService.groupByCity();
        if(maps.size()>0){
            return new ResultResponse(200,"success",maps);
        }
        return new ResultResponse(500,"error",null);
    }
}
