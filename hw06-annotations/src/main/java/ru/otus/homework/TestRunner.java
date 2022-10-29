package ru.otus.homework;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;
import ru.otus.homework.data.TestMethodsData;
import ru.otus.homework.data.TestResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class TestRunner {

    private TestRunner() {
    }

    public static void startTest(String className) throws ReflectiveOperationException {
        Class<?> testClass = Class.forName(className);
        TestMethodsData testMethods = findTestMethods(testClass);
        TestResult testResult = invokeTests(testMethods, testClass.getConstructor());
        printResult(testResult);
    }

    private static TestMethodsData findTestMethods(Class<?> testClass) {
        Method before = null;
        Method after = null;
        Set<Method> testMethods = new HashSet<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
            if (method.isAnnotationPresent(Before.class)) {
                before = method;
            }
            if (method.isAnnotationPresent(After.class)) {
                after = method;
            }
        }
        return new TestMethodsData(before, after, testMethods);
    }

    private static TestResult invokeTests(TestMethodsData methods, Constructor<?> constructor) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<String> failTest = new HashSet<>();
        Set<String> successTest = new HashSet<>();
        Method before = methods.before();
        Method after = methods.after();

        for (Method testMethod : methods.testMethods()) {
            Object o = constructor.newInstance();
            try {
                if (before != null) {
                    before.invoke(o);
                }
                testMethod.invoke(o);
                successTest.add(testMethod.getName());
            } catch (Exception e) {
                failTest.add(String.format("%s (%s)", testMethod.getName(), e.getCause().getMessage()));
            } finally {
                if (after != null) {
                    after.invoke(o);
                }
            }
        }
        return new TestResult(successTest, failTest);
    }

    private static void printResult(TestResult testResult) {
        int testCount = testResult.successTest().size() + testResult.failTest().size();
        System.out.println("Всего тестов: " + testCount);
        System.out.println(testResult.successTest().size() + " тестов прошло:");
        printTestName(testResult.successTest());
        System.out.println(testResult.failTest().size() + " тестов провалено:");
        printTestName(testResult.failTest());
    }

    private static void printTestName(Set<String> names) {
        System.out.println("----------------------");
        names.forEach(System.out::println);
        System.out.println("----------------------");
    }
}