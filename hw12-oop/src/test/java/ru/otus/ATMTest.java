package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ATMTest {

    private int serialStart;

    @BeforeEach
    void init() {
        serialStart = 0;
    }


    @Nested
    @DisplayName("Тесты на добавление купюр в банкомат")
    class ATMAddTest {
        /**
         * Если в банкомат добавляют купюру номиналом, который банкомат может обслуживать,
         * то банкомат возвращает пустой список, сумма в банкомате увеличивается на номинал этой купюры
         */
        @Test
        void test1() {
            ATMImpl atm = new ATMImpl(new Denomination[]{Denomination.TWO_HUNDRED});
            List<Banknote> badBanknotes = atm.addBanknotes(createBatchBanknote(Denomination.TWO_HUNDRED, 1));

            assertEquals(0, badBanknotes.size());
            assertEquals(200, atm.getSum());
        }

        /**
         * Если в банкомат добавляют купюру номиналом, который банкомат не обслуживает,
         * то банкомат возвращает такую купюру, сумма в банкомате не меняется
         */
        @Test
        void test2() {
            ATMImpl atm = new ATMImpl(new Denomination[]{Denomination.ONE});
            List<Banknote> badBanknotes = atm.addBanknotes(createBatchBanknote(Denomination.TWO_HUNDRED, 1));

            assertEquals(1, badBanknotes.size());
            assertEquals(Denomination.TWO_HUNDRED, badBanknotes.get(0).denomination());
            assertEquals(0, atm.getSum());
        }
    }

    @Nested
    @DisplayName("Тесты на получение денег")
    class ATMGiveMoneyTest {
        private ATMImpl atm;

        @BeforeEach
        void init() {
            atm = new ATMImpl(Denomination.values());
        }

        /**
         * Если в банкомате одна банкнота номиналом 1 и запрошена сумма 1,
         * то банкомат выдаёт эту банкноту,
         * в банкомате остаётся 0
         */
        @Test
        void test1() {
            atm.addBanknotes(createBatchBanknote(Denomination.ONE, 1));

            List<Banknote> banknotes = atm.giveMoney(1);

            assertEquals(1, banknotes.size());
            assertEquals(Denomination.ONE, banknotes.get(0).denomination());
            assertEquals(0, atm.getSum());
        }

        /**
         * Если в банкомате пять банкнот номиналом 1 и банкнота номиналом 5 запрошена сумма 5,
         * то банкомат выдаёт банкноту номиналом 5,
         * в банкомате остаётся 5
         */
        @Test
        void test2() {
            atm.addBanknotes(createBatchBanknote(Denomination.ONE, 5));
            atm.addBanknotes(createBatchBanknote(Denomination.FIVE, 1));

            List<Banknote> banknotes = atm.giveMoney(5);

            assertEquals(1, banknotes.size());
            assertEquals(Denomination.FIVE, banknotes.get(0).denomination());
            assertEquals(5, atm.getSum());
        }

        /**
         * Если в банкомате пять банкнот номиналом 1 и банкнота номиналом 5 запрошена сумма 4,
         * то банкомат выдаёт четыре банкноты номиналом 1,
         * в банкомате остаётся 6
         */
        @Test
        void test3() {
            atm.addBanknotes(createBatchBanknote(Denomination.ONE, 5));
            atm.addBanknotes(createBatchBanknote(Denomination.FIVE, 1));

            List<Banknote> banknotes = atm.giveMoney(4);

            assertEquals(4, banknotes.size());
            assertEquals(Denomination.ONE, banknotes.get(0).denomination());
            assertEquals(6, atm.getSum());
        }

        /**
         * Если в банкомате пять банкнот номиналом 200 и две банкноты номиналом 100 запрошена сумма 500,
         * то банкомат выдаёт две банкноты номиналом 200 и одна банкнота номиналом 100,
         * в банкомате остаётся 700
         */
        @Test
        void test4() {
            atm.addBanknotes(createBatchBanknote(Denomination.TWO_HUNDRED, 5));
            atm.addBanknotes(createBatchBanknote(Denomination.HUNDRED, 2));

            List<Banknote> banknotes = atm.giveMoney(500);

            assertEquals(3, banknotes.size());
            banknotes.sort(Comparator.comparing(banknote -> banknote.denomination().getValue()));

            assertEquals(Denomination.HUNDRED, banknotes.get(0).denomination());
            assertEquals(Denomination.TWO_HUNDRED, banknotes.get(1).denomination());
            assertEquals(Denomination.TWO_HUNDRED, banknotes.get(2).denomination());
            assertEquals(700, atm.getSum());
        }

        /**
         * Если в банкомате есть по одной банкноте каждого номинала, и запрашиваемая сумма 6366,
         * то банкомат выдаёт по одной банкноте каждого типа,
         * в банкомате остаётся 0
         */
        @Test
        void test5() {
            Denomination[] denominations = Denomination.values();
            Arrays.stream(denominations)
                    .forEach(d -> atm.addBanknotes(createBatchBanknote(d, 1)));

            List<Banknote> banknotes = atm.giveMoney(6366);

            assertEquals(denominations.length, banknotes.size());
            Set<Denomination> collect = banknotes.stream().map(Banknote::denomination).collect(Collectors.toSet());
            for (Denomination denomination : denominations) {
                assertTrue(collect.contains(denomination));
            }
            banknotes.sort(Comparator.comparing(banknote -> banknote.denomination().getValue()));
            assertEquals(0, atm.getSum());
        }

        /**
         * Если в банкомате пять банкнот номиналом 200 и две банкноты номиналом 100 запрошена сумма 550,
         * то банкомат бросит исключение
         */
        @Test
        void test6() {
            atm.addBanknotes(createBatchBanknote(Denomination.TWO_HUNDRED, 5));
            atm.addBanknotes(createBatchBanknote(Denomination.HUNDRED, 2));

            assertThrows(
                    IllegalStateException.class,
                    () -> atm.giveMoney(550),
                    "Не возможно получить требуемую сумму");
        }


    }


    private List<Banknote> createBatchBanknote(Denomination denomination, int count) {
        List<Banknote> banknotes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            banknotes.add(new Banknote(serialStart++, denomination));
        }
        return banknotes;
    }

}