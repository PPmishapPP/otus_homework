package ru.otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        ClassLoader classLoader = getClass().getClassLoader();
        Gson gson = new Gson();
        try (Reader inputStream = new InputStreamReader(classLoader.getResourceAsStream(fileName))) {
            Type type = new TypeToken<ArrayList<Measurement>>() {
            }.getType();
            return gson.fromJson(inputStream, type);
        } catch (Exception ignore) {
            //ignore
        }

        return Collections.emptyList();
    }
}
