package edu.tinkoff.translator.exception;

import edu.tinkoff.translator.controller.dto.TranslateWordsErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    @Test
    @DisplayName("handleHttpClientErrorException должен возвращать внутреннюю ошибку сервера, если обнаруживает " +
            "HttpClientErrorException c 401 кодом")
    void handleHttpClientErrorExceptionTest() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        TranslateWordsErrorResponse response = new TranslateWordsErrorResponse("Yandex cloud authorization token error");

        ResponseEntity<TranslateWordsErrorResponse> responseEntity = new GlobalExceptionHandler().handleHttpClientErrorException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(response.message(), responseEntity.getBody().message());
    }

    @Test
    @DisplayName("handleDataAccessException корректно обрабатывает DataAccessException")
    void handleDataAccessExceptionTest() {
        DataAccessException exception = new DataAccessResourceFailureException("Ошибка подключения к базе данных");
        TranslateWordsErrorResponse responseBody = new TranslateWordsErrorResponse("Ошибка подключения к базе данных");
        ResponseEntity<TranslateWordsErrorResponse> expected =
                ResponseEntity.status(HttpStatus.valueOf(500)).body(responseBody);

        ResponseEntity<TranslateWordsErrorResponse> actual = new GlobalExceptionHandler().handleDataAccessException(exception);

        assertEquals(expected, actual);
    }


}
