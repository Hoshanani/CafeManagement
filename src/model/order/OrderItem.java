package model.order;

import model.product.Product;
import java.io.Serializable;

public class OrderItem implements Serializable {

    private final String productId;
    private final String productName;
    private final double unitPrice;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.unitPrice = product.getPrice();
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }

    public double total() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return productName + " x" + quantity + " = " + String.format("%.2f", total()) + " TL";
    }
}
