package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ProcessorEvenSecond implements Processor {

    private final Supplier<LocalDateTime> dateTimeSupplier;

    public ProcessorEvenSecond(Supplier<LocalDateTime> dateTimeSupplier) {
        this.dateTimeSupplier = dateTimeSupplier;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime localDateTime = dateTimeSupplier.get();
        int second = localDateTime.getSecond();
        if (second % 2 == 0) {
            throw new IllegalStateException("Сообщение " + message.getId() + " попало на четную секунду " + second);
        }
        return message;
    }
}
