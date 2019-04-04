package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.model.ReciverInfo;
import com.lee.seckillshop.mapper.ReciveInfoMapper;
import com.lee.seckillshop.service.ReciveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciveInfoServiceImpl implements ReciveInfoService {
    @Autowired
    private ReciveInfoMapper reciveInfoMapper;
    @Override
    public Boolean addReciveInfo(ReciverInfo reciverInfo) {
        return reciveInfoMapper.addReciveInfo(reciverInfo)==1?true:false;
    }

    @Override
    public ReciverInfo getRecive(String orderId) {
        return reciveInfoMapper.getRecive(orderId);
    }

    @Override
    public Boolean update(ReciverInfo reciverInfo) {
        return reciveInfoMapper.update(reciverInfo);
    }
}
