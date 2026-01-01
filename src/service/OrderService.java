package service;

import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import model.order.Order;
import model.order.OrderItem;
import model.order.OrderStatus;
import model.people.Customer;
import model.product.Product;
import util.IdGenerator;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Sipariş yönetimi:
 * - sipariş oluşturma
 * - siparişe ürün ekleme
 * - ödeme sonrası "PAID" işaretleme
 */
public class OrderService {

    private final Map<String, Order> orders = new LinkedHashMap<>();

    public OrderService(List<Order> loadedOrders) {
        for (Order o : loadedOrders) {
            orders.put(o.getId(), o);
        }
    }

    public Collection<Order> all() {
        return orders.values();
    }

    public String createOrder(Customer customer) {
        String id = IdGenerator.newOrderId();
        Order order = new Order(id, customer);
        orders.put(id, order);
        return id;
    }

    public Order get(String orderId) {
        Order o = orders.get(orderId);
        if (o == null) throw new NotFoundException("Sipariş bulunamadı: " + orderId);
        return o;
    }

    public void addItem(Order order, Product product, int qty) {
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new InvalidInputException("Siparişe sadece CREATED durumunda ürün eklenebilir.");
        }
        order.addItem(new OrderItem(product, qty));
    }

    public void markPaid(Order order) {
        if (order.getItems().isEmpty()) {
            throw new InvalidInputException("Sipariş boş, ödeme alınamaz.");
        }
        order.setStatus(OrderStatus.PAID);
    }
}
