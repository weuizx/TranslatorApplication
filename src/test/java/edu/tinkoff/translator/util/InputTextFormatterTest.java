package edu.tinkoff.translator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static edu.tinkoff.translator.util.InputTextFormatter.createBatches;
import static edu.tinkoff.translator.util.InputTextFormatter.toCanonicalForm;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InputTextFormatterTest {
    @Test
    @DisplayName("toCanonicalForm должен удалять лишние пробелы между словами, пробелы в начале и конце строки")
    void toCanonicalFormTest2() {
        String input = "    Крайние      и     промежуточные     пробелы     должны быть удалены       ";
        String expected = "Крайние и промежуточные пробелы должны быть удалены";

        assertEquals(expected, toCanonicalForm(input));
    }

    @Test
    @DisplayName("Знак препинания примыкает к предыдущему слову(при наличии), за знаком препинания следует пробел")
    void toCanonicalFormTest3() {
        String input = "Знак ,препинания ?, примыкает !!!  к  предыдущему ;слову .";
        String expected = "Знак, препинания? , примыкает! ! ! к предыдущему; слову.";

        assertEquals(expected, toCanonicalForm(input));
    }

    @Test
    @DisplayName("toCanonicalForm корректно обрабатывает предложение, включающее все случаи")
    void toCanonicalFormTest1() {
        String input = "     ,, Дорогие ??друзья, постоянное . информационно-техническое ?" +
                " обеспечение нашей деятельности представляет собой интересный" +
                " эксперимент проверки системы    .  ";
        String expected = ", , Дорогие? ? друзья, постоянное." +
                " информационно-техническое? обеспечение " +
                "нашей деятельности представляет собой интересный эксперимент проверки системы.";

        assertEquals(expected, toCanonicalForm(input));
    }

    @Test
    @DisplayName("createBatches должен возвращать лист содержащий единственный лист со всеми словами" +
            " входного текста(Текст меньше 20 слов)")
    void createBatchesTest1() {
        String input = "createBatches должен возвращать лист содержащий единственный лист со всеми словами" +
                " входного текста (Текст меньше 20 слов)";

        List<List<String>> expected = List.of(List.of(
                "createBatches",
                "должен",
                "возвращать",
                "лист",
                "содержащий",
                "единственный",
                "лист",
                "со",
                "всеми",
                "словами",
                "входного",
                "текста",
                "(Текст",
                "меньше",
                "20",
                "слов)"
        ));

        assertEquals(expected, createBatches(input, 20));
    }

    @Test
    @DisplayName("createBatches должен возвращать лист листов размером не больше 20 со всеми словами" +
            " входного текста (Текст больше 20 слов)")
    void createBatchesTest2() {
        String input = "createBatches должен возвращать лист ,?!; листов размером не больше 20 со всеми словами" +
                " входного текста (Текст больше 20 слов)";

        List<List<String>> expected = List.of(
                List.of(
                        "createBatches",
                        "должен",
                        "возвращать",
                        "лист,",
                        "?",
                        "!",
                        ";",
                        "листов",
                        "размером",
                        "не",
                        "больше",
                        "20",
                        "со",
                        "всеми",
                        "словами",
                        "входного",
                        "текста",
                        "(Текст",
                        "больше",
                        "20"
                ),
                List.of(
                        "слов)"
                )
        );

        assertEquals(expected, createBatches(input, 20));
    }
}
