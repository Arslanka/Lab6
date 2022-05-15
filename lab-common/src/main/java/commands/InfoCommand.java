package commands;

import collection.Collection;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class InfoCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public InfoCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        collection.info();
        printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        return null;
    }

    @Override
    public boolean withArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Displays information about the collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{};
    }
}
