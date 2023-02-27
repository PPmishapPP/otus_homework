package ru.outs;

public class Enumerator extends Thread {

    private final boolean isFirst;
    private final Printer printer;

    public Enumerator(String name, Printer printer, boolean isFirst) {
        super(name);
        this.isFirst = isFirst;
        this.printer = printer;
    }

    @Override
    public void run() {
        try {
            String threadName = getName() + ": ";
            synchronized (printer) {
                while (!Thread.currentThread().isInterrupted()) {
                    for (int i = 1; i < 10; i++) {
                        while (isFirst != printer.isFirst()) {
                            printer.wait();
                        }
                        printer.print(threadName + i);
                        printer.notifyAll();
                    }
                    for (int i = 10; i > 1; i--) {
                        while (isFirst != printer.isFirst()) {
                            printer.wait();
                        }
                        printer.print(threadName + i);
                        printer.notifyAll();
                    }
                }
            }
        } catch (InterruptedException ignore) {
        }
    }

}
