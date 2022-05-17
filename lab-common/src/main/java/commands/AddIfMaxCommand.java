package commands;

import collection.Collection;
import data.Dragon;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class AddIfMaxCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public AddIfMaxCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            Dragon dragon = (Dragon) args[0];
            dragon.setId(Collection.mex());
            builder.append(collection.addIfMax(dragon));
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
