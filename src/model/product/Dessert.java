package model.product;

// Tatlı ürünü
public class Dessert extends Product {

    public Dessert(String id, String name, double price, int stock) {
        super(id, name, price, stock);
    }

    public Category getCategory() {
        return Category.DESSERT;
    }
}
