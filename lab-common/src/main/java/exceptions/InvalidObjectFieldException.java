package exceptions;

public class InvalidObjectFieldException extends RuntimeException {
    public InvalidObjectFieldException(String message) {
        super(message);
    }

    public InvalidObjectFieldException(String message, Throwable cause) {
        super(message, cause);
    }


}
