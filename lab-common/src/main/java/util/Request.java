package util;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;

public class Request implements Serializable {
    private String clientInfo;
    private LocalTime currentTime;
    private String commandName;
    private final Object[] commandArgs;

    public Request(String commandName, Object... commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    public Request(Object... commandArgs) {
        this.commandArgs = commandArgs;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object[] getCommandArgs() {
        return commandArgs;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public boolean wasExit() {
        return "exit".equals(commandName);
    }

    @Override
    public String toString() {
        return "Request{" +
                "commandName='" + commandName + '\'' +
                ", commandArgs=" + Arrays.toString(commandArgs) +
                '}';
    }
}
