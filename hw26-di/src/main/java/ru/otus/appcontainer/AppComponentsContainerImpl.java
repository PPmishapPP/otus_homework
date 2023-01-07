package ru.otus.appcontainer;

import lombok.SneakyThrows;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @SneakyThrows
    private void processConfig(Class<?> configClass) {

        Map<Integer, List<R>> map = new TreeMap<>();
        for (Method method : configClass.getDeclaredMethods()) {
            AppComponent annotation = method.getAnnotation(AppComponent.class);
            if (annotation != null) {
                map.computeIfAbsent(annotation.order(), i -> new ArrayList<>()).add(new R(method, annotation));
            }
        }
        Object o = configClass.getConstructor().newInstance();
        for (List<R> rs : map.values()) {
            for (R r : rs) {
                Parameter[] parameters = r.method.getParameters();
                Object[] objects = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String name = parameter.getName();
                    objects[i] = appComponentsByName.get(name);
                }
                Object bean = r.method.invoke(o, objects);
                appComponents.add(bean);
                appComponentsByName.put(r.annotation.name(), bean);
            }
        }
    }

    record R (Method method, AppComponent annotation) {

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return null;
    }
}
