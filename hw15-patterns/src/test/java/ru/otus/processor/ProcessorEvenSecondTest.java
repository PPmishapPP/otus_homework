package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProcessorEvenSecondTest {

    @ParameterizedTest
    @MethodSource("evenGenerate")
    @DisplayName("В четные секунды бросается исключение исключение")
    void evenTest(int second) {
        Message message = new Message.Builder(1L).build();

        LocalDateTime even = LocalDateTime.parse("2022-12-17T11:00:00").withSecond(second);
        ProcessorEvenSecond processorEvenSecond = new ProcessorEvenSecond(() -> even);

        assertThatThrownBy(() -> processorEvenSecond.process(message))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Сообщение 1 попало на четную секунду " + second);
    }

    @ParameterizedTest
    @MethodSource("notEvenGenerate")
    @DisplayName("В не четные секунды сообщение остаётся без изменений")
    void notEvenTest(int second) {
        Message message = new Message.Builder(1L).build();

        LocalDateTime notEven = LocalDateTime.parse("2022-12-17T11:00:00").withSecond(second);
        ProcessorEvenSecond processorEvenSecond = new ProcessorEvenSecond(() -> notEven);

        processorEvenSecond.process(message);

        assertThat(processorEvenSecond.process(message)).isSameAs(message);
    }

    public static Stream<Arguments> evenGenerate() {
        return IntStream.range(0, 60).filter(i -> i % 2 == 0).mapToObj(Arguments::of);
    }

    public static Stream<Arguments> notEvenGenerate() {
        return IntStream.range(0, 60).filter(i -> i % 2 != 0).mapToObj(Arguments::of);
    }

}