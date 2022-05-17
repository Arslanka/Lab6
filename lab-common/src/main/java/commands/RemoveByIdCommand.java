package commands;

import collection.Collection;
import exceptions.IncorrectIdException;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class RemoveByIdCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public RemoveByIdCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(collection.removeById((Integer) args[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an id to remove from the collection.\n"));
        } catch (IncorrectIdException e) {
            builder.append(ERROR.wrapped("Deleting an element by id error.\n" + e.getMessage() + '\n'));
        }
        return new Response(builder.toString());
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "Removes an item from the collection by its id";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Integer.class};
    }
}
