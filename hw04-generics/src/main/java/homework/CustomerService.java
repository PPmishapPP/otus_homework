package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {
    TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return exportEntry(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return exportEntry(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> exportEntry(Map.Entry<Customer, String> entry) {
        return Optional.ofNullable(entry)
                .map(e -> new AbstractMap.SimpleImmutableEntry<>(new Customer(e.getKey()), e.getValue()))
                .orElse(null);
    }
}
