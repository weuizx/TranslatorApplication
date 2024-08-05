package edu.tinkoff.translator.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class InputTextFormatter {
    private InputTextFormatter() {
    }

    public static String toCanonicalForm(String input) {

        return input.replaceAll("\\s+", " ")
                .replaceAll("\\s*([.,!?;])", "$1")
                .replaceAll("([.,!?;])\\s*", "$1 ")
                .trim();

    }

    public static List<List<String>> createBatches(String text, int batchSize) {

        String canonicalText = InputTextFormatter.toCanonicalForm(text);

        List<String> words = Arrays.stream(canonicalText.split(" ")).toList();

        return IntStream.range(0, (words.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> words.subList(i * batchSize, Math.min((i + 1) * batchSize, words.size())))
                .toList();
    }
}
