package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;


class ProcessorSwapFieldsTest {

    @Test
    @DisplayName("Поля field11 и field12 меняются местами")
    void swapTest() {
        Message message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .build();

        Message result = new ProcessorSwapFields().process(message);

        assertThat(result.getField11()).isEqualTo("field12");
        assertThat(result.getField12()).isEqualTo("field11");
    }
}