package edu.tinkoff.translator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource", ignoreUnknownFields = false)
public record JdbcConfig(
        String url,
        String username,
        String password
) {

}
