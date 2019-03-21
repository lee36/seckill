package com.lee.seckillshop.config;

import com.lee.seckillshop.commons.componet.ZKComponent;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKConfig {

    @Value("${zk.server}")

    private String connector;

    @Bean
    public RetryPolicy policy(){
        return new RetryNTimes(5, 1000);
    }

    @Bean(initMethod = "start")
    public CuratorFramework curator(){
       return CuratorFrameworkFactory.builder()
                .retryPolicy(policy())
                .connectString(connector)
                .build();
    }
    @Bean(name="seckill",initMethod = "init")
    public ZKComponent seckill(){
        ZKComponent component = new ZKComponent(curator(),"seckill");
        return component;
    }

    @Bean(name="buy",initMethod = "init")
    public ZKComponent buy(){
        ZKComponent component = new ZKComponent(curator(),"buy");
        return component;
    }
}
