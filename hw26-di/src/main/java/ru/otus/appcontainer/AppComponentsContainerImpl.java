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
        checkConfigClass(configClass);
        Collection<List<ComponentDefinition>> componentDefinitionsInOrder = prepareDefinitions(configClass);
        Object o = configClass.getConstructor().newInstance();
        for (List<ComponentDefinition> componentDefinitions : componentDefinitionsInOrder) {
            for (ComponentDefinition cd : componentDefinitions) {
                Object[] args = Arrays.stream(cd.method.getParameters())
                        .map(Parameter::getType)
                        .map(this::getAppComponent)
                        .toArray(Object[]::new);
                Object bean = cd.method.invoke(o, args);
                appComponents.add(bean);
                Object previous = appComponentsByName.put(cd.componentName, bean);
                if (previous != null) {
                    throw new IllegalStateException("В контексте уже существует зависимость с именем " + cd.componentName);
                }
            }
        }
    }

    private Collection<List<ComponentDefinition>> prepareDefinitions(Class<?> configClass) {
        Map<Integer, List<ComponentDefinition>> map = new TreeMap<>();
        for (Method method : configClass.getDeclaredMethods()) {
            AppComponent annotation = method.getAnnotation(AppComponent.class);
            if (annotation != null) {
                map.computeIfAbsent(annotation.order(), i -> new ArrayList<>()).add(new ComponentDefinition(method, annotation.name()));
            }
        }
        return map.values();
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        C result = null;
        for (Object bean : appComponents) {
            if (componentClass.isAssignableFrom(bean.getClass())) {
                if (result == null) {
                    //noinspection unchecked
                    result = (C) bean;
                } else {
                    throw new IllegalStateException("В контексте дублируются зависимости типа " + componentClass);
                }
            }
        }
        if (result == null) {
            throw new IllegalStateException("В контексте не содержится необходимой зависимости типа " + componentClass);
        }
        return result;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object o = appComponentsByName.get(componentName);
        if (o == null) {
            throw new IllegalStateException("В контексте не содержится необходимой зависимости c именем " + componentName);
        }
        //noinspection unchecked
        return (C) o;
    }

    record ComponentDefinition(Method method, String componentName) {
    }
}
