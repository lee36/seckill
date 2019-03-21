package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.model.Advice;
import com.lee.seckillshop.mapper.AdviceMapper;
import com.lee.seckillshop.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdviceServiceImpl implements AdviceService {

    @Autowired
    private AdviceMapper adviceMapper;

    @Override
    public List<Map<String,Object>> getAllAdviceToApp() {
        return adviceMapper.adviceList();
    }

    @Override
    @Transactional
    public Boolean deleteSelected(List<Integer> ids) {
        try {
            ids.stream().forEach((id) -> {
                adviceMapper.deleteById(id);
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Advice getAdvice(Integer id) {
        return adviceMapper.getAdvice(id);
    }

    @Override
    @Transactional
    public Boolean addComment(Integer publish,Advice advice) {
        try {
            adviceMapper.addAdvice(advice);
            adviceMapper.addAdminRepay(publish, advice.getId());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
