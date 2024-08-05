package edu.tinkoff.translator.service;

import edu.tinkoff.translator.repository.TraceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TranslatorServiceImplTest {
    @Autowired
    TranslatorServiceImpl translatorService;
    @Autowired
    TraceRepository traceRepository;

    @Test
    @DisplayName("Сервис корректно обращается к API, возвращает переведенную фразу и создает запись в базе данных" +
            "(Текст меньше 20 слов)")
    @Transactional
    @Rollback
    void translatorServiceImplTest1() {
        String ipAddress = "198.255.0.1";
        String sourceLanguageCode = "en";
        String targetLanguageCode = "es";
        String text = "Hello world";

        String expected = "Hola mundo";
        int initialNumberOfLines = traceRepository.selectCountAll();

        String result = translatorService.translate(ipAddress, sourceLanguageCode, targetLanguageCode, text);

        assertEquals(expected, result);
        assertEquals(1, traceRepository.selectCountAll() - initialNumberOfLines);

    }

    @Test
    @DisplayName("Сервис корректно обращается к API, возвращает переведенную фразу и создает запись в базе данных" +
            "(Текст больше 20 слов)")
    @Transactional
    @Rollback
    void translatorServiceImplTest2() {
        String ipAddress = "198.255.0.1";
        String sourceLanguageCode = "en";
        String targetLanguageCode = "es";
        String text = "A Christian holiday signifying the birth of Jesus, " +
                "Christmas is widely celebrated and enjoyed across " +
                "the United States and the world. The holiday always " +
                "falls on 25 December (regardless of the day of the week), " +
                "and is typically accompanied by decorations, presents, and special meals.";

        String expected = "A Cristiano vacaciones significado el nacimiento de Jesús, " +
                "Navidad es ampliamente celebrado y disfrutado a través " +
                "el Unidos Estados y el mundo. El vacaciones Siempre caídas " +
                "en 25 Diciembre (independientemente de el día de el semana), " +
                "y es típicamente acompañado por decoraciones, regalos, y especial comidas.";
        int initialNumberOfLines = traceRepository.selectCountAll();

        String result = translatorService.translate(ipAddress, sourceLanguageCode, targetLanguageCode, text);

        assertEquals(expected, result);
        assertEquals(1, traceRepository.selectCountAll() - initialNumberOfLines);

    }

    @Test
    @DisplayName("Метод выбрасывает CompletionException при пустом входном тексте и не создает запись в базе данных")
    @Transactional
    @Rollback
    void translatorServiceImplTest3() {
        String ipAddress = "198.255.0.1";
        String sourceLanguageCode = "en";
        String targetLanguageCode = "es";
        String text = "";

        int initialNumberOfLines = traceRepository.selectCountAll();

        assertThrows(CompletionException.class,
                () ->translatorService.translate(ipAddress, sourceLanguageCode, targetLanguageCode, text));

        assertEquals(0, traceRepository.selectCountAll() - initialNumberOfLines);

    }

    @Test
    @DisplayName("Метод выбрасывает CompletionException при неправильном source/targetLanguageCode и не создает запись в базе данных")
    @Transactional
    @Rollback
    void translatorServiceImplTest4() {
        String ipAddress = "198.255.0.1";
        String sourceLanguageCode = "en";
        String targetLanguageCode = "esp";
        String text = "Hello world";

        int initialNumberOfLines = traceRepository.selectCountAll();

        assertThrows(CompletionException.class,
                () ->translatorService.translate(ipAddress, sourceLanguageCode, targetLanguageCode, text));

        assertEquals(0, traceRepository.selectCountAll() - initialNumberOfLines);

    }
}
