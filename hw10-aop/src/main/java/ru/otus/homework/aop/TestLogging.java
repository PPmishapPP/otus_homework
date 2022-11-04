package ru.otus.homework.aop;

import java.util.ArrayList;
import java.util.List;

public class TestLogging implements TestLoggingInterface {
    @Override
    @Log
    public void calculation(int param) {
        System.out.println("Один параметр int " + param);
    }

    @Override
    @Log
    public void calculation(Integer param) {
        System.out.println("Один параметр Integer " + param);
    }

    @Override
    @Log
    public void calculation(int param1, Integer param2) {
        System.out.println("Два параметра int, Integer " + param1 + " " + param2);
    }

    @Override
    @Log
    public void calculation(Integer param1, int param2) {
        System.out.println("Два параметра Integer, int " + param1 + " " + param2);
    }

    @Override
    @Log
    public void calculation(int param1, String param2, Integer param3) {
        System.out.println("Три параметра int, String, Integer " + param1 + " " + param2 + " " + param3);
    }

    @Override
    @Log
    public void calculation(List<Integer> param1) {
        System.out.println("Один параметр List<Integer> " + param1);
    }

    @Override
    @Log
    public void calculation(ArrayList<Integer> param1) {
        System.out.println("Один параметр ArrayList<Integer> " + param1);
    }
}
