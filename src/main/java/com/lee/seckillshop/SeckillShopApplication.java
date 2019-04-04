package com.lee.seckillshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.lee.seckillshop.mapper")
@EnableScheduling
@EnableSolrRepositories("com.lee.seckillshop.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableAsync(proxyTargetClass = true)
public class SeckillShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillShopApplication.class, args);
    }
}
