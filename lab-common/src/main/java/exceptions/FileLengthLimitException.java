package exceptions;

public class FileLengthLimitException extends InputOutputException {
    public FileLengthLimitException(String message) {
        super(message);
    }

    public FileLengthLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
