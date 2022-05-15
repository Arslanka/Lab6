package commands;

import collection.Collection;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.Serializable;

import static io.ConsoleColor.ERROR;
import static io.ConsoleColor.HELP;

public class ClearCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public ClearCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        StringBuilder builder = new StringBuilder();
        collection.clear();
        builder.append(HELP.wrapped("The collection has been cleared\n"));
        builder.append(ERROR.wrapped(ClientApplication.SEPARATOR));
        return new Response(builder.toString());
    }

    @Override
    public boolean withArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{};
    }


}
