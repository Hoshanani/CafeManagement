package model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.people.Customer;

public class Order implements Serializable {

    private final String id;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double subtotal() {
        return items.stream().mapToDouble(OrderItem::total).sum();
    }

    public double vat() { // مثال: 10% ضريبة
        return subtotal() * 0.10;
    }

    public double total() {
        return subtotal() + vat();
    }

    @Override
    public String toString() {
        return "Order " + id + " | " + customer + " | " + status + " | total=" +
                String.format("%.2f", total()) + " TL";
    }
}
