package commands;

import collection.Collection;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class RemoveAllByWeight implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveAllByWeight(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            Float weight = (Float) args[0];
            builder.append(collection.removeByWeight(weight));
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
