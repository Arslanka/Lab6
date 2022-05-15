package commands;

import collection.Collection;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class RemoveAllByWeight implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveAllByWeight(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            Float weight = (Float) args[0];
            collection.removeByWeight(weight);
            printer.println(String.format("%s %f %s", "Elements whose weight field value is equivalent to", weight, "the specified one have been successfully removed from the collection"), ConsoleColor.HELP);
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
        return "remove_all_by_weight";
    }

    @Override
    public String getDescription() {
        return "Removes from the collection all elements whose weight field value is equivalent to the specified";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Float.class};
    }
}
