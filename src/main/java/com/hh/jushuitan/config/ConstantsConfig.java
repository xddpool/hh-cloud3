package com.hh.jushuitan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxiong
 */
@Data
@Component
@ConfigurationProperties(prefix = "constants")
public class ConstantsConfig {
    private String SIGN_METHOD_MD5;
    private String SIGN_METHOD_HMAC;
    private String CHARSET_UTF8;
    private String CONTENT_ENCODING_GZIP;
    private String SERVER_URL;
    private String APP_KEY;
    private String APP_SECRET;

}
