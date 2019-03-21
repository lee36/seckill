package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.Advice;


import java.util.*;

public interface AdviceService {
    public List<Map<String,Object>> getAllAdviceToApp();
    public Boolean deleteSelected(List<Integer> ids);
    public Advice getAdvice(Integer id);
    public Boolean addComment(Integer publish,Advice advice);
}
