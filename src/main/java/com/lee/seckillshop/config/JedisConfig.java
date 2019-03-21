package com.lee.seckillshop.config;

import com.lee.seckillshop.commons.properties.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @date 2018-09-19
 * jedis的配置文件类
 */
@Configuration
public class JedisConfig {

    @Autowired
    private JedisProperties jedisProperties;

    @Bean
    public JedisPoolConfig config(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(200);
        config.setMinIdle(50);//设置最小空闲数
        config.setMaxWaitMillis(20000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        return config;
    }
    /**
     * 注入jedis集群配置
     *
     * @return
     */
    @Bean
    public JedisCluster jedisCluster(JedisPoolConfig config) {
        List<String> nodes = jedisProperties.getNodes();
        Set<HostAndPort> ipAddr = new HashSet<>();
        for (String node : nodes) {
            String[] hostPort = node.split(":");
            HostAndPort hostAndPort = new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1]));
            ipAddr.add(hostAndPort);
        }
        JedisCluster cluster = new JedisCluster(ipAddr,config);
        return cluster;
    }
}
