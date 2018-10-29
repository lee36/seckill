package com.lee.seckillshop;

import com.lee.seckillshop.config.RabbitConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.lee.seckillshop.mapper")
@EnableScheduling
@EnableSolrRepositories("com.lee.seckillshop.mapper")
@EnableAspectJAutoProxy
@EnableAsync
public class SeckillShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillShopApplication.class, args);
    }
}
