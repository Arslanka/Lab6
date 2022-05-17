package commands;

import collection.Collection;
import data.Dragon;
import exceptions.ExecutionException;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class UpdateIdCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public UpdateIdCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(collection.updateId((Integer) args[0], (Dragon) args[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an item to add to the collection."));
        } catch (ExecutionException e) {
            builder.append(ERROR.wrapped(e.getMessage()));
        }
        return new Response(builder.toString());
    }


    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Updates the value of a collection item whose id is equal to the specified one";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Integer.class, Dragon.class};
    }
}
