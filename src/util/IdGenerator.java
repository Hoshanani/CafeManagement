package util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger ORDER_SEQ = new AtomicInteger(1000);

    public static String newOrderId() {
        return "S" + ORDER_SEQ.getAndIncrement();
    }
}
