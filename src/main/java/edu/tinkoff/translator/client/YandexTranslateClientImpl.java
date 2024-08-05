package edu.tinkoff.translator.client;

import edu.tinkoff.translator.client.dto.TranslateTextResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class YandexTranslateClientImpl implements YandexTranslateClient {
    private final RestTemplate restTemplate;
    private final String authToken;
    private final String folderId;
    private final String yandexTranslatorBaseUrl;


    @Override
    public TranslateTextResponse fetchTranslate(String sourceLanguageCode, String targetLanguageCode, String word) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);

        Map<String, Object> body = Map.of(
                "sourceLanguageCode", sourceLanguageCode,
                "targetLanguageCode", targetLanguageCode,
                "texts", List.of(word),
                "folder_id", folderId
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);


        return restTemplate.postForEntity(yandexTranslatorBaseUrl, entity, TranslateTextResponse.class).getBody();
    }

}
