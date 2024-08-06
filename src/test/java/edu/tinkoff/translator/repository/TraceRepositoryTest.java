package edu.tinkoff.translator.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TraceRepositoryTest {
    @Autowired
    TraceRepository traceRepository;

    @Test
    @DisplayName("Корректная запись сохраняется в таблицу")
    @Transactional
    @Rollback
    void addTest1() {
        String ipAddress = "192.168.0.1";
        String sourceText = "Hello";
        String translatedText = "Hola";
        String sourceLang = "en";
        String targetLang = "es";

        int result = traceRepository.add(ipAddress, sourceText, translatedText, sourceLang, targetLang);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Добавление значени null в поле ipAddress вызывает ошибку, запись не добавляется")
    @Transactional
    @Rollback
    void addTest2() {
        String ipAddress = null;
        String sourceText = "Hello";
        String translatedText = "Hola";
        String sourceLang = "en";
        String targetLang = "es";


        int result = traceRepository.add(ipAddress, sourceText, translatedText, sourceLang, targetLang);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Добавление значени null в поле sourceText вызывает ошибку, запись не добавляется")
    @Transactional
    @Rollback
    void addTest3() {
        String ipAddress = "192.168.0.1";
        String sourceText = null;
        String translatedText = "Hola";
        String sourceLang = "en";
        String targetLang = "es";

        int result = traceRepository.add(ipAddress, sourceText, translatedText, sourceLang, targetLang);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Добавление значени null в поле translatedText вызывает ошибку, запись не добавляется")
    @Transactional
    @Rollback
    void addTest4() {
        String ipAddress = "192.168.0.1";
        String sourceText = "Hello";
        String translatedText = null;
        String sourceLang = "en";
        String targetLang = "es";

        int result = traceRepository.add(ipAddress, sourceText, translatedText, sourceLang, targetLang);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Добавление значени null в поле sourceLang вызывает ошибку, запись не добавляется")
    @Transactional
    @Rollback
    void addTest5() {
        String ipAddress = "192.168.0.1";
        String sourceText = "Hello";
        String translatedText = "Hola";
        String sourceLang = null;
        String targetLang = "es";

        int result = traceRepository.add(ipAddress, sourceText, translatedText, sourceLang, targetLang);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Добавление значени null в поле targetLang вызывает ошибку, запись не добавляется")
    @Transactional
    @Rollback
    void addTest6() {
        String ipAddress = "192.168.0.1";
        String sourceText = "Hello";
        String translatedText = "Hola";
        String sourceLang = "en";
        String targetLang = null;

        int result = traceRepository.add(ipAddress, sourceText, translatedText, sourceLang, targetLang);

        assertEquals(0, result);
    }


}
