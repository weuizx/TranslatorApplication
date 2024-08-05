package edu.tinkoff.translator.exception;

import edu.tinkoff.translator.controller.dto.TranslateWordsErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<TranslateWordsErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode().value() == 401) {
            TranslateWordsErrorResponse body = new TranslateWordsErrorResponse("Yandex cloud authorization token error");
            return ResponseEntity.internalServerError().body(body);
        }
        TranslateWordsErrorResponse body = e.getResponseBodyAs(TranslateWordsErrorResponse.class);
        return ResponseEntity.status(e.getStatusCode()).body(body);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<TranslateWordsErrorResponse> handleDataAccessException(DataAccessException e) {
        TranslateWordsErrorResponse body = new TranslateWordsErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(500)).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TranslateWordsErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        StringBuilder errors = new StringBuilder("Validation errors: ");
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );
        return ResponseEntity.badRequest().body(new TranslateWordsErrorResponse(errors.toString()));
    }
}
