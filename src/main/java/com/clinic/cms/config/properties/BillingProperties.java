package com.clinic.cms.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "clinic.billing")
public class BillingProperties {

    private String receiptPrefix;
}