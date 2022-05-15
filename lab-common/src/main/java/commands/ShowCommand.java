package commands;

import collection.Collection;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Request;
import util.Response;

import java.io.Serializable;

public class ShowCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public ShowCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        collection.show();
        printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        return null;
    }

    @Override
    public boolean withArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Outputs all elements of the collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{};
    }
}
