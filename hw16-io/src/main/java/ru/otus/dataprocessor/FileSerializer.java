package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(json);
        } catch (Exception ignore) {
            //ignore
        }
    }
}
