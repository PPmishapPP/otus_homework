package ru.otus.homework.aop;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestLogging(new TestLogging());
        testLogging.calculation(1);
        testLogging.calculation(Integer.valueOf(2));
        testLogging.calculation(3, Integer.valueOf(4));
        testLogging.calculation(Integer.valueOf(5), 6);
        testLogging.calculation(7, "8", 9);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(10);
        testLogging.calculation(integerList);

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(11);
        testLogging.calculation(integerArrayList);
    }
}
