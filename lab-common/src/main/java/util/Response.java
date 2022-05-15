package util;

import java.io.Serializable;
import java.util.Arrays;

public class Response implements Serializable {
    private final String responseMessage;
    private Object[] args;

    public Response(String responseMessage, Object... args) {
        this.responseMessage = responseMessage;
        this.args = args;
    }


    public String getMessage() {
        return this.responseMessage;
    }

    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseMessage='" + responseMessage + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
