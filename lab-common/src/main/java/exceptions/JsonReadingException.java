package exceptions;

public class JsonReadingException extends FIleReadingException {
    public JsonReadingException(String message) {
        super(message);
    }

    public JsonReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
