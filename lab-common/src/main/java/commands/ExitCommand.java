package commands;

import io.ConsoleColor;
import io.Printer;
import util.Request;
import util.Response;

import java.io.Serializable;

public class ExitCommand implements Command, Serializable {
    private final Printer printer;

    public ExitCommand(Printer printer) {
        this.printer = printer;
    }


    @Override
    public Response execute(Object... args) {
        printer.println("The program is completed", ConsoleColor.ERROR);
        return null;
    }

    @Override
    public boolean withArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public String getDescription() {
        return "Stops program execution";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{};
    }
}
