package service;

import model.order.Order;
import model.order.OrderStatus;

import java.util.concurrent.BlockingQueue;

/*
 * Mutfak işçisi (Thread/Runnable):
 * - Kuyruktan PAID siparişleri alır
 * - PREPARING -> READY durumuna geçirir
 * - Hazırlama süresini simüle eder
 */
public class KitchenWorker implements Runnable {

    private final BlockingQueue<Order> queue;
    private volatile boolean running = true;

    public KitchenWorker(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Kuyruktan sipariş al (bloklanır)
                Order order = queue.take();

                order.setStatus(OrderStatus.PREPARING);
                System.out.println("[MUTFAK] Hazırlanıyor: " + order.getId());

                // En az 2 saniye, item sayısına göre artar
                int seconds = Math.max(2, order.getItems().size());
                Thread.sleep(seconds * 1000L);

                order.setStatus(OrderStatus.READY);
                System.out.println("[MUTFAK] HAZIR: " + order.getId());

            } catch (InterruptedException e) {
                // Program kapanırken thread interrupt alabilir
            } catch (Exception ex) {
                System.out.println("[MUTFAK] HATA: " + ex.getMessage());
            }
        }
    }
}
