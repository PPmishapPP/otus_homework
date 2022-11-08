package ru.otus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ATMImpl implements ATM {

    private final Map<Denomination, BanknoteBox> boxes = new TreeMap<>(
            Comparator.comparing(Denomination::getValue).reversed()
    );

    public ATMImpl(Denomination[] denominations) {
        for (Denomination denomination : denominations) {
            boxes.put(denomination, new BanknoteBoxImpl(denomination));
        }
    }

    @Override
    public List<Banknote> addBanknotes(List<Banknote> banknotes) {
        List<Banknote> badBanknotes = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            BanknoteBox banknoteBox = boxes.get(banknote.denomination());
            if (banknoteBox == null) {
                badBanknotes.add(banknote);
            } else {
                banknoteBox.addBanknote(banknote);
            }
        }
        return badBanknotes;
    }

    @Override
    public List<Banknote> giveMoney(int sum) {
        Map<Denomination, Integer> result = new EnumMap<>(Denomination.class);
        for (Map.Entry<Denomination, BanknoteBox> entry : boxes.entrySet()) {
            int need = sum / entry.getKey().getValue();
            if (need >= 1) {
                int have = Math.min(entry.getValue().count(), need);
                sum = sum - have * entry.getKey().getValue();
                result.put(entry.getKey(), have);
            }
            if (sum == 0) {
                List<Banknote> banknotes = new ArrayList<>();
                for (Map.Entry<Denomination, Integer> e : result.entrySet()) {
                    banknotes.addAll(boxes.get(e.getKey()).getBanknote(e.getValue()));
                }
                return banknotes;
            }
        }
        throw new IllegalStateException("Не возможно получить требуемую сумму");
    }

    @Override
    public int getSum() {
        int sum = 0;
        for (Map.Entry<Denomination, BanknoteBox> entry : boxes.entrySet()) {
            int value = entry.getKey().getValue();
            int count = entry.getValue().count();
            sum += value * count;
        }
        return sum;
    }
}
