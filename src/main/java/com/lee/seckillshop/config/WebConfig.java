package com.lee.seckillshop.config;

import com.lee.seckillshop.intercepter.RegistIntercepter;
import com.lee.seckillshop.intercepter.TokenInterceper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author admin
 * @date 2018-09-20
 * springmvc的配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RegistIntercepter registIntercepter;
    @Autowired
    private TokenInterceper tokenInterceper;
    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(registIntercepter).addPathPatterns("/user/regist");
      registry.addInterceptor(tokenInterceper).addPathPatterns("/seckill/**");
    }

    /**
     * 配置跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedMethods("*").allowedOrigins("*");
    }
    /**
     * 配置pageable相关的参数注入
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolvers.add(resolver);
    }

    /**
     * 设置restTemplate用于构建请求
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
