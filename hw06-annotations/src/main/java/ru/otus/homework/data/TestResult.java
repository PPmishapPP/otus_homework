package ru.otus.homework.data;

import java.util.Set;

public record TestResult(Set<String> successTest, Set<String> failTest) {
}
