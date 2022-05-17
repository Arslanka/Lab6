package commands;

import collection.Collection;
import data.Dragon;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class RemoveLowerCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveLowerCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            Dragon dragon = (Dragon) args[0];
            dragon.setId(Collection.mex());
            builder.append(collection.removeLower((Dragon) args[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an element for comparison.\n"));
        }
        return new Response(builder.toString());
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String getDescription() {
        return "Removes all items smaller than the specified one from the collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Dragon.class};
    }
}
