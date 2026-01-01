package service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import model.order.Order;
import model.payment.CardPayment;
import model.payment.CashPayment;
import model.payment.PaymentMethod;
import model.payment.PaymentStrategy;
import model.people.Customer;
import model.product.Product;
import repository.AppPaths;
import repository.CsvProductStore;
import repository.SerializationStore;

/*
 * Uygulamanın ana servisi (Facade):
 * - ürün yükleme (CSV)
 * - sipariş yönetimi (OrderService)
 * - stok yönetimi (InventoryService)
 * - ödeme işlemi (Strategy)
 * - mutfak kuyruğu ve thread
 * - kayıt/yükleme (Serialization)
 */
public class CafeService {

    private final SerializationStore serStore = new SerializationStore();
    private final CsvProductStore csvStore = new CsvProductStore();

    private final InventoryService inventory;
    private final OrderService orders;

    private final BlockingQueue<Order> kitchenQueue = new ArrayBlockingQueue<>(100);
    private final KitchenWorker kitchenWorker = new KitchenWorker(kitchenQueue);
    private Thread kitchenThread;

    public CafeService() {
        // Ürünleri CSV'den yükle (yoksa varsayılan oluşturur)
        List<Product> products = csvStore.loadOrCreateDefaults();

        // Daha önce kayıtlı siparişleri yükle
        List<Order> loadedOrders = serStore.loadListOrEmpty(AppPaths.ORDERS_SER);

        this.inventory = new InventoryService(products);
        this.orders = new OrderService(loadedOrders);
    }

    public void startKitchen() {
        kitchenThread = new Thread(kitchenWorker, "KitchenWorker");
        kitchenThread.start();
    }

    public String createOrder(String customerName, String phone) {
        Customer c = new Customer(customerName, phone);
        return orders.createOrder(c);
    }

    public void addItemToOrder(String orderId, String productId, int qty) {
        Order o = orders.get(orderId);

        // önce stok ayır
        inventory.reserveStock(productId, qty);

        // sonra siparişe ekle
        Product p = inventory.findById(productId);
        orders.addItem(o, p, qty);
    }

    public void checkout(String orderId, PaymentMethod method) throws InterruptedException {
        Order o = orders.get(orderId);

        // Ödeme stratejisi seçimi (Polymorphism)
        PaymentStrategy strategy =
                (method == PaymentMethod.CARD) ? new CardPayment() : new CashPayment();

        // Order.java içinde toplamTutar() var (senin Türkçe sürümünle uyumlu)
        double amount = o.total();
        strategy.pay(amount);

        // ödendi işaretle ve mutfağa gönder
        orders.markPaid(o);
        kitchenQueue.put(o);
    }

    public void listProducts() {
        System.out.println("\n--- Ürünler ---");
        inventory.all().forEach(System.out::println);
    }

    public void listOrders() {
        System.out.println("\n--- Siparişler ---");
        for (Order o : orders.all()) {
            System.out.println(o);
            o.getItems().forEach(it -> System.out.println("   - " + it));
        }
    }

    public void shutdown() {
        // Siparişleri kaydet
        serStore.saveList(AppPaths.ORDERS_SER, orders.all().stream().toList());

        // Mutfak thread'ini kapat
        kitchenWorker.stop();
        if (kitchenThread != null) kitchenThread.interrupt();
    }
}

