package exceptions;

public class InvalidCommandNameException extends RuntimeException {
    public InvalidCommandNameException(String message) {
        super(message);
    }

    public InvalidCommandNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
