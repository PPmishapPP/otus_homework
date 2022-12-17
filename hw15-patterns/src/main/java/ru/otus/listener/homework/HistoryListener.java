package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        Message.Builder builder = msg.toBuilder();
        if (msg.getField13() != null) {
            ObjectForMessage objectForMessage = new ObjectForMessage();
            objectForMessage.setData(new ArrayList<>(msg.getField13().getData()));
            builder.field13(objectForMessage);
        }
        map.put(msg.getId(), builder.build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(map.get(id));
    }
}
