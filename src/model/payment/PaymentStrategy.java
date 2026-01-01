package model.payment;

// Ödeme stratejisi arayüzü
public interface PaymentStrategy {
    void pay(double amount);
}
