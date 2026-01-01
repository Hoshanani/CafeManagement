package model.payment;

// Kart ile ödeme işlemi
public class CardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Karttan çekildi: " + amount + " TL");
    }
}
