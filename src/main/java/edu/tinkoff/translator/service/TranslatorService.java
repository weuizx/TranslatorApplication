package edu.tinkoff.translator.service;

public interface TranslatorService {
    String translate(String ipAddress, String sourceLanguageCode, String targetLanguageCode, String text);
}
