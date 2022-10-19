package ru.otus.homework;

import java.util.Objects;

public class Cat implements Comparable<Cat>{
    private final String name;
    private final int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat cat)) return false;
        return age == cat.age && Objects.equals(name, cat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public int compareTo(Cat o) {
        return Integer.compare(age, o.age);
    }

    public String getName() {
        return name;
    }
}
