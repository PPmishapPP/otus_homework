package ru.otus;

import java.util.List;

public interface BanknoteBox {
    void addBanknote(Banknote banknote);

    List<Banknote> getBanknote(int count);

    int count();
}
