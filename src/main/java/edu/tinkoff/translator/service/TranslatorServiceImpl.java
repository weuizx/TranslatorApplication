package edu.tinkoff.translator.service;

import edu.tinkoff.translator.client.YandexTranslateClient;
import edu.tinkoff.translator.client.dto.TranslateTextResponse;
import edu.tinkoff.translator.repository.TraceRepository;
import edu.tinkoff.translator.util.InputTextFormatter;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TranslatorServiceImpl implements TranslatorService {
    private static final int REQUEST_LIMIT = 20;
    private final ExecutorService executorService;
    private final YandexTranslateClient yandexTranslateClient;
    private final TraceRepository traceRepository;

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public String translate(String ipAddress, String sourceLanguageCode, String targetLanguageCode, String text) {

        List<List<String>> wordBatches = InputTextFormatter.createBatches(text, REQUEST_LIMIT);

        List<List<String>> translatedWords = new ArrayList<>();

        for (List<String> batch : wordBatches) {
            translatedWords.add(translateBatch(sourceLanguageCode, targetLanguageCode, batch));
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        String translatedText = translatedWords.stream().flatMap(List::stream).collect(Collectors.joining(" "));

        traceRepository.add(
                ipAddress,
                text,
                translatedText,
                sourceLanguageCode,
                targetLanguageCode
        );

        return translatedText;
    }


    private List<String> translateBatch(String sourceLanguageCode, String targetLanguageCode, List<String> words) {

        List<CompletableFuture<TranslateTextResponse>> futures = words.stream()
                .map(word -> CompletableFuture
                        .supplyAsync(() -> yandexTranslateClient.fetchTranslate(sourceLanguageCode, targetLanguageCode, word),
                                executorService)).toList();

        return futures.stream().map(CompletableFuture::join).map(word -> word.translations().getFirst().text()).toList();
    }

    //    public String translate(String ipAddress, String sourceLanguageCode, String targetLanguageCode, String text) {
//        String canonicalText = InputTextFormatter.toCanonicalForm(text);
//
//        List<String> words = Arrays.stream(canonicalText.split(" ")).toList();
//
//        List<CompletableFuture<TranslateTextResponse>> futures = words.stream()
//                .map(word -> CompletableFuture
//                        .supplyAsync(() -> yandexTranslateClient.fetchTranslate(sourceLanguageCode, targetLanguageCode, word),
//                    executorService)).toList();
//
//        List<String> translatedWords =
//                futures.stream().map(CompletableFuture::join).map(word -> word.translations().getFirst().text()).toList();
//
//        String translatedText = String.join(" ", translatedWords);
//        traceRepository.add(
//                ipAddress,
//                text,
//                translatedText,
//                sourceLanguageCode,
//                targetLanguageCode
//        );
//
//        return translatedText;
//    }
}
