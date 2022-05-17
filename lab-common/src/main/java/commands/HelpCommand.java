package commands;

import io.Printer;
import util.Response;

import java.io.Serializable;
import java.util.Map;

import static io.ConsoleColor.HELP;

public class HelpCommand implements Command, Serializable {
    private final Printer printer;
    private final Map<String, Command> commandMap;

    public HelpCommand(Printer printer, Map<String, Command> commandMap) {
        this.printer = printer;
        this.commandMap = commandMap;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (String entry : commandMap.keySet()) {
            builder.append(HELP.wrapped(String.format("%-30s", entry) + " " + commandMap.get(entry).getDescription())).append('\n');
        }
        return new Response(builder.toString());
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