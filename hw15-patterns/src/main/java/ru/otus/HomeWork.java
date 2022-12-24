package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorEvenSecond;
import ru.otus.processor.ProcessorSwapFields;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) throws InterruptedException {
        ComplexProcessor complexProcessor = new ComplexProcessor(
                List.of(new ProcessorEvenSecond(LocalDateTime::now), new ProcessorSwapFields()),
                e -> System.out.println(e.getMessage())
        );
        HistoryListener historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        for (int i = 0; i < 20; i++) {
            Thread.sleep(400);
            Message message = new Message.Builder(i).field11("11_" + i).field12("12_" + i).build();
            complexProcessor.handle(message);
        }

        for (int i = 0; i < 25; i++) {
            Optional<Message> optionalMessage = historyListener.findMessageById(i);
            if (optionalMessage.isPresent()) {
                System.out.println(optionalMessage.get());
            } else {
                System.out.println("Сообщение " + i + " не обрабатывалось");
            }
        }
    }
}
