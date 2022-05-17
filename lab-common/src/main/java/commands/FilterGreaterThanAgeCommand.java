package commands;

import collection.Collection;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class FilterGreaterThanAgeCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public FilterGreaterThanAgeCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            Long age = (Long) args[0];
            builder.append(collection.filterGreaterThanAge(age));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an element to filter by collection."));
        }
        return new Response(builder.toString());
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
