package app;

import model.payment.PaymentMethod;
import service.CafeService;
import util.Input;
    
// Programın çalıştığı ana sınıf
public class Main {

    // Programın başlangıç noktası
    public static void main(String[] args) {

        // Kafe servis nesnesi oluşturulur
        CafeService cafe = new CafeService();

        // Mutfak thread'i başlatılır
        cafe.startKitchen();

        System.out.println("=== Cafe Management System ===");

        // Ana menü döngüsü
        while (true) {

            // Menü seçenekleri
            System.out.println("\n1) Ürünleri Listele");
            System.out.println("2) Sipariş Oluştur");
            System.out.println("3) Siparişe Ürün Ekle");
            System.out.println("4) Ödeme Yap");
            System.out.println("5) Siparişleri Listele");
            System.out.println("6) Çıkış");

            int choice = Input.readInt("Seçiminiz: ");

            try {
                switch (choice) {

                    // Ürünleri listele
                    case 1 -> cafe.listProducts();

                    // Yeni sipariş oluştur
                    case 2 -> {
                        String name = Input.readLine("Müşteri adı: ");
                        String phone = Input.readLine("Telefon: ");
                        System.out.println("Sipariş ID: " + cafe.createOrder(name, phone));
                    }

                    // Siparişe ürün ekle
                    case 3 -> {
                        String orderId = Input.readLine("Sipariş ID: ");
                        String productId = Input.readLine("Ürün ID: ");
                        int qty = Input.readInt("Adet: ");
                        cafe.addItemToOrder(orderId, productId, qty);
                    }

                    // Ödeme işlemi
                    case 4 -> {
                        String orderId = Input.readLine("Sipariş ID: ");
                        System.out.println("1) Nakit  2) Kart");
                        int pm = Input.readInt("Ödeme: ");
                        cafe.checkout(orderId,
                                pm == 2 ? PaymentMethod.CARD : PaymentMethod.CASH);
                    }

                    // Siparişleri listele
                    case 5 -> cafe.listOrders();

                    // Programdan çık
                    case 6 -> {
                        cafe.shutdown();
                        System.out.println("Program kapatıldı");
                        return;
                    }
                }

            } catch (InterruptedException e) {
                // Hata mesajı gösterilir
                System.out.println("yarida kesildi: " + e.getMessage());
                Thread.currentThread().interrupt();
            
            } catch (RuntimeException e) {
                // Hata mesajı gösterilir
                System.out.println("HATA: " + e.getMessage());
            }
        }
    }
}
