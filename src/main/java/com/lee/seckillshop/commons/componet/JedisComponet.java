package com.lee.seckillshop.commons.componet;


import com.lee.seckillshop.commons.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2018-09-19
 * 自定义jedis的模板工具
 */
@Component
public class JedisComponet {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 设置值
     *
     * @param key
     * @param value
     * @param second 过期时间，null为永久
     * @return
     * @throws Exception
     */
    public String set(String key, Object value, Long second) throws Exception {
            String s = JsonUtil.obj2Json(value);
            String result = null;
            if (second == null) {
                redisTemplate.opsForValue().set(key,s);
            } else {
                redisTemplate.opsForValue().set(key,s,second,TimeUnit.SECONDS);
            }

        return result;
    }
    public void multi(){
        redisTemplate.multi();
    }
    public void exec(){
        redisTemplate.exec();
    }
    public void disCard(){
        redisTemplate.discard();
    }
    /**
     * 通过key获取对应的alue
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T get(String key, Class<T> clazz) throws Exception {
        String s1 = redisTemplate.opsForValue().get(key);
        T t = JsonUtil.json2Obj(s1, clazz);
        return t;
    }

    /**
     * 增加相应的key的值
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        Long incr = redisTemplate.opsForValue().increment(key,1);
        return incr;
    }

    /**
     * 减少相应的key的值
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        return redisTemplate.opsForValue().increment(key,-1);
    }

    /**
     * 用户访问量
     */
    public void userVisited() {
        String user_visited = redisTemplate.opsForValue().get("user:visited");
        if (user_visited == null) {
            redisTemplate.opsForValue().set("user:visited", "1");
        } else {
            redisTemplate.opsForValue().increment("user:visited",1);
        }
    }

    /**
     * 删除某个key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
