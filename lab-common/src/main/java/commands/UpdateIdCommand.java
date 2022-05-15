package commands;

import collection.Collection;
import data.Dragon;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class UpdateIdCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public UpdateIdCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            collection.updateId((Integer) args[0], (Dragon) args[1]);
            printer.println("Collection item with id " + args[0] + " successfully updated ", ConsoleColor.HELP);
            printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ExecutionException("You have not entered an item to add to the collection.");
        }
        return null;
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Updates the value of a collection item whose id is equal to the specified one";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Integer.class, Dragon.class};
    }
}
