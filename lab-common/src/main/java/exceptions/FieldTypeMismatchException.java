package exceptions;

public class FieldTypeMismatchException extends InvalidObjectFieldException {
    public FieldTypeMismatchException(String message) {
        super(message);
    }

    public FieldTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
