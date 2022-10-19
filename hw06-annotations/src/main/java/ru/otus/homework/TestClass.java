package ru.otus.homework;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TestClass {

    private Cat cat1;
    private Cat cat2;
    private Cat cat3;

    @Before
    public void init() {
        cat1 = new Cat("Барсик", 7);
        cat2 = new Cat("Снежок", 5);
        cat3 = new Cat("Мурзик", 5);
    }

    @Test
    public void hashSetTest() {
        setTest(new HashSet<>());
    }

    @Test
    public void treeSetTest() {
        setTest(new TreeSet<>());
    }

    private void setTest(Set<Cat> cats) {
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);

        Set<String> names = cats.stream()
                .map(Cat::getName)
                .collect(Collectors.toSet());

        if (!names.contains(cat1.getName())) {
            throw new RuntimeException("Нет кота с именем " + cat1.getName());
        }
        if (!names.contains(cat2.getName())) {
            throw new RuntimeException("Нет кота с именем " + cat2.getName());
        }
        if (!names.contains(cat3.getName())) {
            throw new RuntimeException("Нет кота с именем " + cat3.getName());
        }
    }

    @After
    public void after() {
        System.out.println("after");
    }
}
