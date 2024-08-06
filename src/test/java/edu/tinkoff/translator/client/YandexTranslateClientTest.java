package edu.tinkoff.translator.client;

import edu.tinkoff.translator.client.dto.TranslateTextResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class YandexTranslateClientTest {
    @Autowired
    YandexTranslateClient client;

    @Test
    @DisplayName("Корректный запрос должен возвращать корректный перевод")
    void yandexTranslateClientTest1() {

        TranslateTextResponse response = client.fetchTranslate("en", "ru", "Hello");

        assertEquals("Здравствуйте", response.translations().getFirst().text());
    }

    @Test
    @DisplayName("Запрос с неправильными кодом языка должен возвращать 400 код")
    void yandexTranslateClientTest2() {

        assertThrows(HttpClientErrorException.class, () -> client.fetchTranslate("wrongLang", "ru", "Hello"));
    }

    @Test
    @DisplayName("Запрос с пустым переводимым словом должен возвращать 400 код")
    void yandexTranslateClientTest3() {

        assertThrows(HttpClientErrorException.class, () -> client.fetchTranslate("en", "ru", ""));
    }


}
