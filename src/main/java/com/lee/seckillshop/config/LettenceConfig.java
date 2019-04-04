package com.lee.seckillshop.config;

import com.beust.jcommander.internal.Lists;
import com.lee.seckillshop.commons.properties.JedisProperties;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.masterslave.MasterSlaveConnectionProvider;
import io.lettuce.core.models.role.RedisNodeDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


/**
 * @author admin
 * @date 2018-09-19
 * jedis的配置文件类
 */
@Configuration
public class LettenceConfig {
//    @Bean
//    public ReadFrom readFrom(){
//        ReadFrom from = ReadFrom.valueOf("slave");
//        ReadFrom.Nodes nodes = new ReadFrom.Nodes(){
//           private List<RedisNodeDescription> list=null;
//            @Override
//            public Iterator<RedisNodeDescription> iterator() {
//                return list.iterator();
//            }
//
//            @Override
//            public List<RedisNodeDescription> getNodes() {
//                List<RedisNodeDescription> list = new ArrayList<>();
//                for (int i=1;i<=5;i++){
//                   RedisClusterNode clusterNode = new RedisClusterNode();
//                   clusterNode.setUri(RedisURI.create("118.31.64.158",Integer.parseInt("900"+i)));
//                   list.add(clusterNode);
//                }
//               this.list=list;
//               return list;
//            }
//        };
//        return from;
//    }
//    @Bean
//    public LettuceClientConfigurationBuilderCustomizer builderCustomizer(){
//       return (clientConfigurationBuilder)->{
//               clientConfigurationBuilder.readFrom(readFrom()).build();
//       };
//    }
    @Bean
    public StringRedisTemplate redisTemplate(LettuceConnectionFactory connectionFactory){
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
        return template;
    }
}
