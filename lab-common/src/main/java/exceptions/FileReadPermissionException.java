package exceptions;

public class FileReadPermissionException extends InputOutputException {
    public FileReadPermissionException(String message) {
        super(message);
    }

    public FileReadPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
