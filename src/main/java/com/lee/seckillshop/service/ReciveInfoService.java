package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.model.ReciverInfo;

public interface ReciveInfoService {
    public Boolean addReciveInfo(ReciverInfo reciverInfo);

    ReciverInfo getRecive(String orderId);

    Boolean update(ReciverInfo reciverInfo);
}
