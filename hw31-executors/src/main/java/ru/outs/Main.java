package ru.outs;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Printer printer = new Printer();
        Thread first = new Enumerator("Поток 1", printer, true);
        Thread second = new Enumerator("Поток 2", printer, false);

        second.start();
        Thread.sleep(10);

        first.start();
        Thread.sleep(100);

        first.interrupt();
        second.interrupt();
    }
}
