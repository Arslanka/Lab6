package commands;

import collection.Collection;
import data.Dragon;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class RemoveLowerCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveLowerCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            collection.removeLower((Dragon) args[0]);
            printer.println("Elements whose value is lower than the specified value have been successfully removed from the collection", ConsoleColor.HELP);
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
        return "remove_lower";
    }

    @Override
    public String getDescription() {
        return "Removes all items smaller than the specified one from the collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Dragon.class};
    }
}
