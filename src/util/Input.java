package util;

import java.util.Scanner;

public class Input {
    private static final Scanner sc = new Scanner(System.in);

    public static String readLine(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Hatalı sayı, tekrar deneyin.");
            }
        }
    }
}
