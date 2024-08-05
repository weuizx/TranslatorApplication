//package edu.tinkoff.translator.exception;
//
//import edu.tinkoff.translator.controller.dto.TranslateWordsErrorResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.HttpClientErrorException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class GlobalExceptionHandlerTest {
//    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
//
//    @Test
//    @DisplayName("Handler корректно обрабатывает HttpClientErrorException")
//    void handleHttpClientErrorExceptionTest() {
//        String responseBody = "{\"message\": \"Недопустимый sourceLanguageCode\"}";
//        TranslateWordsErrorResponse response = new TranslateWordsErrorResponse("Недопустимый sourceLanguageCode");
//        HttpClientErrorException exception = new HttpClientErrorException(
//                HttpStatus.BAD_REQUEST,
//                "Недопустимый sourceLanguageCode",
//                responseBody.getBytes(),
//                null);
//        ResponseEntity<TranslateWordsErrorResponse> expected = ResponseEntity.badRequest().body(response);
//
//        assertEquals(expected, globalExceptionHandler.handleHttpClientErrorException(exception));
//    }
//
//}
