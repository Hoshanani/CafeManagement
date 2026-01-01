package repository;

import java.nio.file.Path;

/*
 * Dosya yollarını tek yerde toplamak için.
 * - data klasörü
 * - products.csv (ürünler)
 * - orders.ser (siparişler - serialization)
 */
public class AppPaths {
    public static final Path DATA_DIR = Path.of("data");
    public static final Path PRODUCTS_CSV = DATA_DIR.resolve("products.csv");
    public static final Path ORDERS_SER   = DATA_DIR.resolve("orders.ser");
}
