package model.product;

// İçecek ürünü
public class Drink extends Product {

    public Drink(String id, String name, double price, int stock) {
        super(id, name, price, stock);
    }

    public Category getCategory() {
        return Category.DRINK;
    }
}
