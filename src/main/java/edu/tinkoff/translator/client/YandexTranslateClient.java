package edu.tinkoff.translator.client;

import edu.tinkoff.translator.client.dto.TranslateTextResponse;
import org.springframework.http.ResponseEntity;

public interface YandexTranslateClient {
    TranslateTextResponse fetchTranslate(String sourceLanguageCode, String targetLanguageCode, String word);
}
