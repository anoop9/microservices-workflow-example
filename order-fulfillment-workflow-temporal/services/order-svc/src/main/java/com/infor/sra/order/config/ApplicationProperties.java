package com.infor.sra.order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties
public class ApplicationProperties {
    private String target;
    private String workflowId;
}
