package service;

import exceptions.NotFoundException;
import exceptions.OutOfStockException;
import model.product.Product;

import java.util.List;

/*
 * Stok/ürün yönetimi:
 * - ürün listeleme
 * - id ile ürün bulma
 * - stok ayırma (reserveStock)
 */
public class InventoryService {

    private final List<Product> products;

    public InventoryService(List<Product> products) {
        this.products = products;
    }

    public List<Product> all() {
        return products;
    }

    public Product findById(String id) {
        return products.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ürün bulunamadı: " + id));
    }

    public void reserveStock(String productId, int qty) {
        if (qty <= 0) {
            throw new OutOfStockException("Adet 0'dan büyük olmalı.");
        }

        Product p = findById(productId);

        if (p.getStock() < qty) {
            throw new OutOfStockException("Stok yetersiz: " + p.getName());
        }

        // stok düş
        p.decreaseStock(qty);
    }
}
