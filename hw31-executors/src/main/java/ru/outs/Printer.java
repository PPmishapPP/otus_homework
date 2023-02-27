package ru.outs;

public class Printer {
    private boolean first = true;

    public void print(String s) {
        System.out.println(s);
        first = ! first;
    }

    public boolean isFirst() {
        return first;
    }
}
