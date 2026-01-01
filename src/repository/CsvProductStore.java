package repository;

import model.product.Category;
import model.product.Dessert;
import model.product.Drink;
import model.product.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/*
 * Ürünleri CSV dosyasından okur (File I/O).
 * CSV formatı: id,name,price,stock,category
 * Örnek: P100,Espresso,70.0,30,DRINK
 */
public class CsvProductStore {

    public List<Product> loadOrCreateDefaults() {
        try {
            Files.createDirectories(AppPaths.DATA_DIR);

            // Dosya yoksa varsayılan ürünleri oluştur
            if (!Files.exists(AppPaths.PRODUCTS_CSV)) {
                List<String> defaults = List.of(
                        "P100,Espresso,70.0,30,DRINK",
                        "P101,Latte,90.0,25,DRINK",
                        "P200,Cheesecake,140.0,12,DESSERT",
                        "P201,Cookie,45.0,50,DESSERT"
                );
                Files.write(AppPaths.PRODUCTS_CSV, defaults);
            }

            return readAll();
        } catch (IOException e) {
            throw new RuntimeException("Ürünler yüklenemedi: " + e.getMessage());
        }
    }

    private List<Product> readAll() throws IOException {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(AppPaths.PRODUCTS_CSV)) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length < 5) continue;

                String id = p[0].trim();
                String name = p[1].trim();
                double price = Double.parseDouble(p[2].trim());
                int stock = Integer.parseInt(p[3].trim());
                Category cat = Category.valueOf(p[4].trim());

                Product prod = (cat == Category.DRINK)
                        ? new Drink(id, name, price, stock)
                        : new Dessert(id, name, price, stock);

                products.add(prod);
            }
        }
        return products;
    }
}
