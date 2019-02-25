package com.lee.seckillshop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author admin
 * @date 2018-10-22
 */
@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.queque}")
    private String queque;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routerkey}")
    private String routerKey;

    @Bean
    public Queue queue() {
        return new Queue(queque);
    }

    @Bean
    public Binding binding() {
        return new Binding(queque, Binding.DestinationType.QUEUE, exchange, routerKey, new HashMap<>());
    }
}
