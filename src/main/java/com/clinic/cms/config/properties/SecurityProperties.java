package com.clinic.cms.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "clinic.security")
public class SecurityProperties {

    private String secret;

    private long accessTokenExpiration;

    private long refreshTokenExpiration;
}
