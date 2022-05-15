package commands;

import collection.Collection;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class FilterGreaterThanAgeCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public FilterGreaterThanAgeCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            Long age = (Long) args[0];
            collection.filterGreaterThanAge(age);
            printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ExecutionException("You have not entered an element to filter by collection.");
        }
        return null;
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "filter_greater_than_age";
    }

    @Override
    public String getDescription() {
        return "Outputs elements whose age field value is greater than the specified one";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Long.class};
    }
}
