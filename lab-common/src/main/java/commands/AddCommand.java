package commands;

import collection.Collection;
import data.Dragon;
import exceptions.ExistingIdException;
import exceptions.InvalidObjectFieldException;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;

public class AddCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public AddCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) throws ExistingIdException {
        StringBuilder builder = new StringBuilder();
        try {
            Dragon dragon = (Dragon) args[0];
            dragon.setId(Collection.mex());
            builder.append(collection.add(dragon));
        } catch (ArrayIndexOutOfBoundsException e) {
            builder.append(ERROR.wrapped("You have not entered an item to add to the collection.\n"));
        } catch (InvalidObjectFieldException | ExistingIdException e) {
            builder.append(ERROR.wrapped("Error executing the add command\n" + e.getMessage() + '\n'));
        }
        return new Response(builder.toString());
    }


    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Adds an item to the collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{Dragon.class};
    }

}
