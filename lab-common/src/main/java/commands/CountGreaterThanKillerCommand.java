package commands;


import collection.Collection;
import data.Person;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class CountGreaterThanKillerCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public CountGreaterThanKillerCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            Person person = (Person) args[0];
            builder.append(collection.countGreaterThanKiller(person));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an item to add to the collection."));
        }
        return new Response(builder.toString());
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
