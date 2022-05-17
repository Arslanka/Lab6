package commands;

import collection.Collection;
import io.Printer;
import util.Response;

import java.io.Serializable;

public class ClearCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public ClearCommand(Collection collection, Printer printer) {
        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        return new Response(collection.clear());
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
