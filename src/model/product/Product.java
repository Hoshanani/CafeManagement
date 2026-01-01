package model.product;

import java.io.Serializable;

// Tüm ürünler için soyut sınıf
public abstract class Product implements Serializable {

    protected String id;
    protected String name;
    protected double price;
    protected int stock;

    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Ürün kategorisi alt sınıflarda belirlenir
    public abstract Category getCategory();

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    // Stok azaltma işlemi
    public void decreaseStock(int q) {
        stock -= q;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + price + " TL | Stok: " + stock;
    }
}
