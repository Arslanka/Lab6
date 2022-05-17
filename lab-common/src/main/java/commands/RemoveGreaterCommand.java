package commands;

import collection.Collection;
import data.Dragon;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class RemoveGreaterCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveGreaterCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            Dragon dragon = (Dragon) args[0];
            dragon.setId(Collection.mex());
            builder.append(collection.removeGreater(dragon));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an element for comparison."));
        }
        return new Response(builder.toString());
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
