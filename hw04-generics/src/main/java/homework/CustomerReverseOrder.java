package homework;


import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    Deque<Customer> deque = new ArrayDeque<>();

    public void add(Customer customer) {
        deque.push(customer);
    }

    public Customer take() {
        return deque.pop();
    }
}
