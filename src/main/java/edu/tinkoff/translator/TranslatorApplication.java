package edu.tinkoff.translator;

import edu.tinkoff.translator.configuration.ApplicationConfig;
import edu.tinkoff.translator.configuration.JdbcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, JdbcConfig.class})
public class TranslatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslatorApplication.class, args);
    }

}
