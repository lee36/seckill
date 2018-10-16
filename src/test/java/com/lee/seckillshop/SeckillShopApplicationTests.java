package com.lee.seckillshop;

import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.mapper.GoodSolrDocumentRepository;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.model.User;
import com.lee.seckillshop.vo.GoodSolrDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillShopApplicationTests {
    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodSolrDocumentRepository goodSolrDocumentRepository;

    @Test
    public void jedisTest() throws Exception {
        Integer aa = jedisTemplate.get("aa", Integer.class);
        System.out.println(aa);
    }

    @Test
    public void rabbitTest() {
        System.out.println(rabbitTemplate + "=====");
    }

    @Test
    public void solrTest() {
        System.out.println(solrTemplate.getSolrClient() + "=====");
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("张三");
        userMapper.saveUser(user);
    }

}

