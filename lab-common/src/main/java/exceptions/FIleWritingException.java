package exceptions;

public class FIleWritingException extends InputOutputException {
    public FIleWritingException(String message) {
        super(message);
    }

    public FIleWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
