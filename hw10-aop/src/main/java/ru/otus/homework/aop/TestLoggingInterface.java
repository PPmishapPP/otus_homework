package ru.otus.homework.aop;

import java.util.ArrayList;
import java.util.List;

public interface TestLoggingInterface {

    void calculation(int param);

    void calculation(Integer param);
    void calculation(int param1, Integer param2);

    void calculation(Integer param1, int param2);

    void calculation(int param1, String param2, Integer param3);

    void calculation(List<Integer> param1);

    void calculation(ArrayList<Integer> param1);

}
