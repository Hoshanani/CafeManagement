package exceptions;

// Stok yetersiz olduğunda fırlatılan özel exception
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String msg) {
        super(msg);
    }
}
