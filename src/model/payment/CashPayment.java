package model.payment;

// Nakit ödeme işlemi
public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Nakit ödeme alındı: " + amount + " TL");
    }
}
