package commands;

import collection.Collection;
import data.Dragon;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Request;
import util.Response;

import java.io.Serializable;

public class RemoveGreaterCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveGreaterCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            collection.removeGreater((Dragon) args[0]);
            printer.println("Elements whose value is greater than the specified value have been successfully removed from the collection", ConsoleColor.HELP);
            printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ExecutionException("You have not entered an element for comparison.");
        }
        return null;
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "Removes all items from the collection that exceed the specified";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Dragon.class};
    }
}
