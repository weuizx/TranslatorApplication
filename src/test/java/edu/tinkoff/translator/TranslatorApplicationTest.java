//package edu.tinkoff.translator;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class TranslatorApplicationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void testTranslateWordsIntegration() throws Exception {
//        // Arrange
//        String requestBody = "{\"sourceLanguageCode\": \"en\", \"targetLanguageCode\": \"es\", \"text\": \"Hello world\"}";
//
//        // Act & Assert
//        mockMvc.perform(post("/translate")
//                        .contentType(MediaType.valueOf("application/json"))
//                        .body(requestBody)
//                        .getNativeRequest())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.translatedText").value("Hola mundo"));
//    }
//
//    @Test
//    void testTranslateWords_InvalidRequest() throws Exception {
//        // Act & Assert
//        mockMvc.perform(post("/translate")
//                        .contentType(MediaType.valueOf("application/json"))
//                        .body("{}")
//                        .getNativeRequest())  // Пустое тело запроса
//                .andExpect(status().isBadRequest());
//    }
//}
