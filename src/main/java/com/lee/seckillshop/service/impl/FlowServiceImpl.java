package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.model.Flow;
import com.lee.seckillshop.mapper.FlowMapper;
import com.lee.seckillshop.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private FlowMapper flowMapper;
    @Override
    public List<Flow> lastSixFlow() {
        return flowMapper.findlast6Flow();
    }

    @Override
    public Map<String, Object> fillDateToMap(List<String> times, List<Long> nums, List<Flow> flowList) {
        HashMap<String, Object> map = new HashMap<>();
        for (Flow flow : flowList) {
            times.add(new SimpleDateFormat("yyyy-MM-dd").format(new Date(flow.getLoginTime().getTime())));
            nums.add(flow.getNum());
        }
        map.put("times",times);
        map.put("nums",nums);
        return map;
    }
}
