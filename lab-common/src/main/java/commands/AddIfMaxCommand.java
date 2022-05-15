package commands;

import collection.Collection;
import data.Dragon;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class AddIfMaxCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public AddIfMaxCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            Dragon dragon = (Dragon) args[0];
            collection.addIfMax(dragon);
            printer.println("The item was successfully added to the collection", ConsoleColor.HELP);
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
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "Adds a new element to the collection if its value exceeds the value of the largest element of this collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Dragon.class};
    }
}
