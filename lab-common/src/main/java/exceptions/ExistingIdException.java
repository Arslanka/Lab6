package exceptions;

public class ExistingIdException extends RuntimeException {
    public ExistingIdException(String message) {
        super(message);
    }

    public ExistingIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
