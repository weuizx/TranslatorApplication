package edu.tinkoff.translator.client.dto;

import java.util.List;

public record TranslateTextResponse(
        List<TranslateTextItem> translations
) {
    public record TranslateTextItem(
            String text
    ) {

    }
}
