package com.lee.seckillshop;

import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.mapper.GoodSolrDocumentRepository;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.commons.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillShopApplicationTests {
    @Autowired
    private JedisComponet jedisTemplate;
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

