package edu.tinkoff.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tinkoff.translator.controller.dto.TranslateWordsRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TranslatorApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Пользователь получает корректный перевод в ответ на корректный запрос")
    void test1() throws Exception {
        String sourceLanguageCode = "ru";
        String targetLanguageCode = "en";
        String text = "Привет";

        String expected = "Hi";

        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translatedText").value(expected));
    }

    @Test
    @DisplayName("Пользователь получает корректный перевод в ответ на корректный запрос с пустым sourceLanguageCode")
    void test2() throws Exception {
        String sourceLanguageCode = "";
        String targetLanguageCode = "en";
        String text = "Здравствуйте";

        String expected = "Hello";

        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translatedText").value(expected));
    }

    @Test
    @DisplayName("Пользователь получает корректный перевод в ответ на корректный запрос с текстом содержащим более чем 20 слов")
    void test3() throws Exception {

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

        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translatedText").value(expected));
    }

    @Test
    @DisplayName("Пользователь получает корректный перевод в ответ на корректный запрос с текстом содержащим идущие " +
            "подряд знаки препинания и множественные пробелы")
    void test4() throws Exception {

        String sourceLanguageCode = "ru";
        String targetLanguageCode = "es";
        String text = "     ,, Дорогие ??друзья, постоянное . информационно-техническое ?" +
                " обеспечение нашей деятельности представляет собой интересный" +
                " эксперимент проверки системы    .  ";

        String expected = ", , ¿Queridos? ? amistad, permanente. ¿tecnología" +
                " de la información? seguridad nuestra actividades representa" +
                " nos interesante experimento prueba sistemas.";

        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translatedText").value(expected));
    }

    @Test
    @DisplayName("Пользователь получает статус 400 BAD_REQUEST в ответ на запрос без поля sourceLanguageCode")
    void test5() throws Exception {

        String sourceLanguageCode = null;
        String targetLanguageCode = "en";
        String text = "Здравствуйте";


        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        String expected = "Validation errors: sourceLanguageCode: Source language code must not be null; ";

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expected));
    }

    @Test
    @DisplayName("Пользователь получает статус 400 BAD_REQUEST в ответ на запрос без поля targetLanguageCode")
    void test6() throws Exception {

        String sourceLanguageCode = "ru";
        String targetLanguageCode = "";
        String text = "Здравствуйте";


        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        String expected = "Validation errors: targetLanguageCode: Target language code must not be empty; ";

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expected));
    }

    @Test
    @DisplayName("Пользователь получает статус 400 BAD_REQUEST в ответ на запрос с пустым полем text")
    void test7() throws Exception {

        String sourceLanguageCode = "ru";
        String targetLanguageCode = "en";
        String text = "";


        TranslateWordsRequest request = new TranslateWordsRequest(sourceLanguageCode, targetLanguageCode, text);

        String expected = "Validation errors: text: Text must not be empty; ";

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expected));
    }
}
