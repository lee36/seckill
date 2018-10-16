package com.lee.seckillshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.lee.seckillshop.mapper")
@EnableScheduling
@EnableSolrRepositories("com.lee.seckillshop.mapper")
@EnableAspectJAutoProxy
public class SeckillShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillShopApplication.class, args);
    }
}
