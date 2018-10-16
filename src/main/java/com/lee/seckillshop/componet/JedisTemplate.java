package com.lee.seckillshop.componet;


import com.lee.seckillshop.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import redis.clients.jedis.JedisCluster;

/**
 * @author admin
 * @date 2018-09-19
 * 自定义jedis的模板工具
 */
@Component
public class JedisTemplate {
   @Autowired
   private JedisCluster jedisCluster;
    /**
     * 设置值
     * @param key
     * @param value
     * @param second 过期时间，null为永久
     * @return
     * @throws Exception
     */
   public String set(String key,Object value,Integer second) throws Exception{
       String s = JsonUtil.obj2Json(value);
       String result=null;
       if(second==null){
           result  = jedisCluster.set(key,s);
       }else {
           result = jedisCluster.set(key, s, "NX", "EX", second);
       }
       return result;
   }

    /**
     * 通过key获取对应的alue
     * @param key
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
   public <T> T get(String key,Class<T> clazz) throws Exception {
       String s = jedisCluster.get(key);
       T t = JsonUtil.json2Obj(s, clazz);
       return t;
   }

    /**
     * 增加相应的key的值
     * @param key
     * @return
     */
   public Long incr(String key){
       Long incr = jedisCluster.incr(key);
       return incr;
   }

    /**
     * 减少相应的key的值
     * @param key
     * @return
     */
   public Long decr(String key){
       return jedisCluster.decr(key);
   }
    /**
     * 用户访问量
     */
    public void userVisited(){
        String user_visited = jedisCluster.get("user:visited");
        if(user_visited==null){
             jedisCluster.set("user:visited", "1");
        }else{
            jedisCluster.incr("user:visited");
        }
    }
    /**
     * 删除某个key
     */
    public void delete(String key){
        jedisCluster.del(key);
    }
}
