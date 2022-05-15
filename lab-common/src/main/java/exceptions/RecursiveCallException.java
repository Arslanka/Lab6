package exceptions;

public class RecursiveCallException extends RuntimeException {
    public RecursiveCallException(String execScript) {
        super(String.format(
                "Recursive call of instruction execute_script. You are in the process of executing the script: %s",
                execScript));
    }

    public RecursiveCallException(String execScript, Throwable cause) {
        super(String.format(
                "Recursive call of instruction execute_script. You are in the process of executing the script: %s",
                execScript, cause));
    }
}
