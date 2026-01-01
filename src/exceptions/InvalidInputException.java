package exceptions;

// Geçersiz kullanıcı girişi durumunda kullanılır
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String msg) {
        super(msg);
    }
}
