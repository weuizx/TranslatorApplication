package edu.tinkoff.translator.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TranslateWordsRequest(
        @NotNull(message = "Source language code must not be null")
        String sourceLanguageCode,
        @NotBlank(message = "Target language code must not be empty")
        String targetLanguageCode,
        @NotBlank(message = "Text must not be empty")
        String text
) {
}
