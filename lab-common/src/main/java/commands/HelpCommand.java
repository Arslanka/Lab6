package commands;

import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;
import java.util.Map;

public class HelpCommand implements Command, Serializable {
    private final Printer printer;
    private final Map<String, Command> commandMap;

    public HelpCommand(Printer printer, Map<String, Command> commandMap) {
        this.printer = printer;
        this.commandMap = commandMap;
    }

    @Override
    public Response execute(Object... args) {
        for (String entry : commandMap.keySet()) {
            printer.println((String.format("%-30s", entry) + " " + commandMap.get(entry).getDescription()), ConsoleColor.HELP);
        }
        printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        return null;
    }

    @Override
    public boolean withArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displays the names and descriptions of all commands";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{};
    }

}