package commands;

import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class ExitCommand implements Command, Serializable {
    private final Printer printer;

    public ExitCommand(Printer printer) {
        this.printer = printer;
    }


    @Override
    public Response execute(Object... args) {
        return new Response(ERROR.wrapped("The program is completed"), "exit");

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
