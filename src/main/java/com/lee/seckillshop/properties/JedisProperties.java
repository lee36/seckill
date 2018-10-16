package com.lee.seckillshop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 * @date 2018-09-19
 */
@Component
@ConfigurationProperties(prefix = "jedis")
@Data
public class JedisProperties {

    private List<String> nodes;


}
