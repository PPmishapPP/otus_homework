package ru.otus;

import java.util.List;

public interface ATM {
    List<Banknote> addBanknotes(List<Banknote> banknotes);

    List<Banknote> giveMoney(int sum);

    int getSum();
}
