package edu.tinkoff.translator.configuration;

import edu.tinkoff.translator.client.YandexTranslateClient;
import edu.tinkoff.translator.client.YandexTranslateClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ComponentsConfiguration {
    @Bean
    public ExecutorService executorService(ApplicationConfig applicationConfig) {
        return Executors.newFixedThreadPool(applicationConfig.numberOfParallelExecutors());
    }

    @Bean
    public YandexTranslateClient yandexTranslateClient(ApplicationConfig applicationConfig) {
        return new YandexTranslateClientImpl(
                new RestTemplate(),
                applicationConfig.authToken(),
                applicationConfig.folderId(),
                applicationConfig.yandexTranslatorBaseUrl());
    }
}
