package com.lee.seckillshop;

import com.lee.seckillshop.mapper.SeckillGoodsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2018-10-12
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDemo {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);

        deque.offer("zhangsan", 1L, TimeUnit.SECONDS);
        deque.offer("lisi", 1L, TimeUnit.SECONDS);
        deque.offer("wangwu", 1L, TimeUnit.SECONDS);
        for (String s : deque) {
            System.out.println(s);
        }
        String poll = deque.poll(1, TimeUnit.SECONDS);
        System.out.println(poll);
    }

    @Test
    public void test1() {
        Arrays.asList(0, 1, 2, 3, 4, 5, 6).stream().forEach(i -> rabbitTemplate.convertAndSend("amq.direct", "seckill.queque", i));
    }

    @Test
    public void testsjal() {
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
    }
}
