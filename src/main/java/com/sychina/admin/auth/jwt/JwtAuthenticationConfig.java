package com.sychina.admin.auth.jwt;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description: jwt配置
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${jwt.url:/login}")
    private String url;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    /**
     * 默认1小时超时
     */
    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;

    @Value("${jwt.secret}")
    private String secret;
}
