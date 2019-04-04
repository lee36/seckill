package com.lee.seckillshop.service.impl;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ImmutableMultiset;
import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.commons.componet.ZKComponent;
import com.lee.seckillshop.commons.exception.SeckillGoodIdNotExistException;
import com.lee.seckillshop.commons.model.*;
import com.lee.seckillshop.commons.util.CommonUtil;
import com.lee.seckillshop.commons.vo.SeckillGoodVo;
import com.lee.seckillshop.mapper.GoodsMapper;
import com.lee.seckillshop.mapper.SeckillGoodsMapper;
import com.lee.seckillshop.mapper.SeckillOrderMapper;
import com.lee.seckillshop.mapper.StoreMapper;
import com.lee.seckillshop.service.GoodSeckillService;
import com.lee.seckillshop.service.GoodsService;
import com.rabbitmq.client.Channel;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.lang.annotation.Target;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2018-10-15
 */
@Service
public class GoodSeckillServiceImpl implements GoodSeckillService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JedisComponet jedisTemplate;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    @Qualifier("seckill")
    private ZKComponent zkComponent;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @RabbitListener(queues = "queque")
    public void dealSeckillDeal(Map<String, Object> ids, Channel channel, Message message) throws Exception {
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            //生成订单
            Integer seckGoodId = (Integer) ids.get("seckGoodId");
            Integer userId = (Integer) ids.get("userId");
            try {
                createOrder(userId,seckGoodId);
            } catch (Exception e) {
                simpMessagingTemplate.convertAndSend("/seckill/"+userId,"抱歉,抢购失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建订单
     *
     * @param userId
     * @param seckGoodId
     */
    @Transactional
    public void createOrder(Integer userId, Integer seckGoodId){
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    SeckillGood seckillGood = seckillGoodsMapper.findSeckillGood(seckGoodId);
                    if(seckillGood==null){
                        throw new SeckillGoodIdNotExistException("秒杀商品不存在");
                    }
                    SeckillOrder seckillOrder = new SeckillOrder();
                    User user = new User();
                    user.setId(userId);
                    seckillOrder.setName(seckillGood.getName());
                    seckillOrder.setPrice(seckillGood.getPrice());
                    seckillOrder.setId(CommonUtil.generateUUID());
                    seckillOrder.setUser(user);
                    seckillOrder.setGood(seckillGood);
                    //生成订单
                    seckillOrderMapper.saveSeckillOrder(seckillOrder);
                    //缓存相应数量减少
                    jedisTemplate.decr("seckill:stock:id:" + seckGoodId);
                    //更新所有缓存秒杀商品
                    updateAllSeckillGoods(seckGoodId);
                    //数据库减少
                    seckillGoodsMapper.updateStockWithOne(seckGoodId);

                    //设置标志位，同一天内不允许同时抢购
                    String day = CommonUtil.dateFormat(new Date(), "yyyy-MM-dd");
                    //禁止再次抢购
                    jedisTemplate.set("seckill:" + day + ":" + userId, 0, 24 * 60 * 60L);
                    //通知用户
                    simpMessagingTemplate.convertAndSend("/seckill/" + userId, "恭喜你抢购成功");
                }catch (Exception e){
                    simpMessagingTemplate.convertAndSend("/seckill/" + userId, "抢购过程中发生异常");
                    transactionStatus.setRollbackOnly();
                }
            }
        });

    }

    @Override
    @Transactional
    public Boolean addSeckillGood(SeckillGood good) {
        //减少数据库库存
        Integer id = good.getGoodId();
        Goods goods = goodsMapper.findById(id);
        if(goods.getStock()<good.getStock()){
             return false;
        }
        goods.setStock(goods.getStock()-good.getStock());
        int i = goodsMapper.updateGoods(goods);
        if(i>0){
            good.setImg(goods.getImg());
            good.setStore(Store.builder().id(goods.getStore().getId()).build());
            good.setName(goods.getName());
            seckillGoodsMapper.addSeckillGood(good);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateSeckillGood(Integer status,Integer id) {
        int i = seckillGoodsMapper.updateStatus(status, id);
        return i==1?true:false;
    }

    @Override
    public List<SeckillGood> getMySelfGoodsList(Integer id) {
        //通过id获取店铺
        Store store = storeMapper.findByUserId(id);
        if(store!=null){
            //
            List<SeckillGood> seckills = seckillGoodsMapper.getMySelfSeckill(store.getId());
            return seckills;
        }
        return null;
    }

    @Override
    public Boolean deleteSelected(List<Integer> ids) {
        try {
            ids.stream().forEach((id) -> {
                seckillGoodsMapper.deleteById(id);
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<List<SeckillGood>> getIndexSeckill() {
        List<SeckillGood> seckildGoods=seckillGoodsMapper.getIndexSeckill();
        List<List<SeckillGood>> list = new ArrayList<>();
        //每页4个
        int size=4;
        //5
        int total=seckildGoods.size();
        //2
        int page=total%size==0?total/size:total/size+1;
        //初始化4个容器
        int start=0;
        int end=4;
        for (int i=0;i<page;i++){
            if(end>total){
                end=total;
            }
            list.add(new ArrayList<>());
            for (int j=start;j<end;j++){
                list.get(i).add(seckildGoods.get(j));
            }
            start+=4;
            end+=4;

        }
        return list;
    }

    @Override
    public SeckillGoodVo getSeckillById(Integer id) {

        SeckillGood seckillGood = seckillGoodsMapper.findSeckillGood(id);
        SeckillGoodVo seckillGoodVo = new SeckillGoodVo();
        BeanUtils.copyProperties(seckillGood,seckillGoodVo);
        return goodsService.addStartTimeAndFinishedTime(Lists.newArrayList(seckillGoodVo)).get(0);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getAllSeckillGood() {
        return seckillGoodsMapper.seckillGoodsList();
    }


    @Override
    public boolean seckillGood(Integer seckGoodId, Integer userId) throws Exception {
        //TimeUnit.SECONDS.sleep(5);
        try {
            zkComponent.getLock(seckGoodId);
            Integer flag = jedisTemplate.get("seckill:disabled:id:" + userId, Integer.class);
            if (new Integer(1).equals(flag)) {
                return false;
            } else {
                Integer stock = jedisTemplate.get("seckill:stock:id:" + seckGoodId, Integer.class);
                if (stock > 0) {
                    Map<String, Object> ids = new HashMap<>();
                    ids.put("seckGoodId", seckGoodId);
                    ids.put("userId", userId);
                    rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText,
                                                      String exchange, String routingKey) -> {
                        System.out.println("seckill返回消息成功" + message.toString());
                    });
                    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                        if (!ack) {
                            System.out.println("seckill消息发送失败" + cause + correlationData.toString());
                        } else {
                            System.out.println("seckill消息发送成功 ");
                        }
                    });
                    rabbitTemplate.convertAndSend("amq.direct", "queque", ids);
                    return true;
                } else {
                    return false;
                }
            }
        }finally {
            zkComponent.realse(seckGoodId);
        }
    }

    //更新缓存中所有商品
    private void updateAllSeckillGoods(Integer seckGoodId) throws Exception {
        List<LinkedHashMap<String, Object>> seckillGoods = jedisTemplate.get("seckill:goods", List.class);
        List<LinkedHashMap<String, Object>> result = new ArrayList<LinkedHashMap<String, Object>>();
        for (LinkedHashMap<String, Object> seckillGood : seckillGoods) {
            Integer id = (Integer) seckillGood.get("id");
            if (id.equals(seckGoodId)) {
                //存在并修改
                Integer stock = (Integer) seckillGood.get("stock");
                stock = stock - 1;
                //修改成功
                seckillGood.put("stock", stock);
            }
            result.add(seckillGood);
        }
        jedisTemplate.set("seckill:goods", result, null);
    }
}
