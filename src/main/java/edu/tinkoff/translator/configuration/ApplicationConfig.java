package edu.tinkoff.translator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        String authToken,
        String folderId,
        String yandexTranslatorBaseUrl,
        int numberOfParallelExecutors
) {
}

