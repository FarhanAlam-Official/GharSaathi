package com.gharsaathi.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private Long expiration; // Access token expiration in milliseconds
    private Long refreshExpiration; // Refresh token expiration in milliseconds
    private String issuer;
}
