package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.Flow;

import java.util.*;

public interface FlowService {
    public List<Flow> lastSixFlow();
    public Map<String,Object> fillDateToMap(List<String> times,List<Long> nums,List<Flow> flows);
}
