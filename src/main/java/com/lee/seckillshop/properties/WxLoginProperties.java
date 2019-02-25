package com.lee.seckillshop.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @date 2018-09-20
 */
@ConfigurationProperties(prefix = "wx.login")
@Component
@Data
public class WxLoginProperties {
    private String appId;
    private String appSecret;
    private String redictUrl;
    private String loginUrl;
    private String accessTokenUrl;
    private String userInfoUrl;
}
