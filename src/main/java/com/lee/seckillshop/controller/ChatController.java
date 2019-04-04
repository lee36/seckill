package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.vo.MsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send/msg")
    public void sendMsg(MsgVo msg){
        System.out.println(msg);
        //发送到目的地
        simpMessagingTemplate.convertAndSend("/msg/"+msg.getTo(),msg);
    }
}
