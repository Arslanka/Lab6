package exceptions;

public class ObjectBuildException extends RuntimeException {
    public ObjectBuildException(String message) {
        super(message);
    }

    public ObjectBuildException(String message, Throwable cause) {
        super(message, cause);
    }

}
