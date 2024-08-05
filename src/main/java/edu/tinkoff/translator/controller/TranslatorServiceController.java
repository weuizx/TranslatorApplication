package edu.tinkoff.translator.controller;

import edu.tinkoff.translator.controller.dto.TranslateWordsOkResponse;
import edu.tinkoff.translator.controller.dto.TranslateWordsRequest;
import edu.tinkoff.translator.service.TranslatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class TranslatorServiceController {
    private final TranslatorService translatorService;

    @PostMapping("/translate")
    public ResponseEntity<TranslateWordsOkResponse> translateWords(HttpServletRequest request, @Valid @RequestBody TranslateWordsRequest requestBody) {
        return ResponseEntity.ok(new TranslateWordsOkResponse(translatorService.translate(
                        request.getRemoteAddr(),
                        requestBody.sourceLanguageCode(),
                        requestBody.targetLanguageCode(),
                        requestBody.text())
                )
        );
    }
}
