package ru.otus.homework.data;

import java.lang.reflect.Method;
import java.util.Set;

public record TestMethodsData(Method before, Method after, Set<Method> testMethods) {
}
