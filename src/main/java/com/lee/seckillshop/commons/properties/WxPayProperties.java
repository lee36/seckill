package com.lee.seckillshop.commons.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @date 2018-09-20
 */
@ConfigurationProperties(prefix = "wx.pay")
@Component
@Data
public class WxPayProperties {
    private String appId;
    private String appSecret;
    private String merId;
    private String key;
    private String backUrl;
    private String payUrl;
}
