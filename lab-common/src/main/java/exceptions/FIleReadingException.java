package exceptions;

public class FIleReadingException extends InputOutputException {
    public FIleReadingException(String message) {
        super(message);
    }

    public FIleReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
