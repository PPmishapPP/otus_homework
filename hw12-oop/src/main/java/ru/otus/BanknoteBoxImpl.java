package ru.otus;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BanknoteBoxImpl implements BanknoteBox {

    private final Deque<Banknote> banknotes = new ArrayDeque<>();
    private final Denomination type;

    public BanknoteBoxImpl(Denomination type) {
        this.type = type;
    }

    @Override
    public void addBanknote(Banknote banknote) {
        if (banknote.denomination() == type) {
            banknotes.push(banknote);
        } else {
            throw new IllegalArgumentException("Эта банкнота не подходит к этой ячейке");
        }
    }

    @Override
    public List<Banknote> getBanknote(int count) {
        List<Banknote> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(banknotes.pop());
        }
        return result;
    }

    @Override
    public int count() {
        return banknotes.size();
    }
}
