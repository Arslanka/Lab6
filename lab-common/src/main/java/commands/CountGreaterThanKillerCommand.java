package commands;


import collection.Collection;
import data.Person;
import exceptions.ExecutionException;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class CountGreaterThanKillerCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public CountGreaterThanKillerCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            Person person = (Person) args[0];
            collection.countGreaterThanKiller(person);
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
        return "count_greater_than_killer";
    }

    @Override
    public String getDescription() {
        return "Outputs the number of elements whose killer field value is greater than the specified one";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Person.class};
    }
}
