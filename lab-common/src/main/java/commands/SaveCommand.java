package commands;

import collection.Collection;
import exceptions.ExecutionException;
import exceptions.InputOutputException;
import file.JsonFile;
import io.ClientApplication;
import io.ConsoleColor;
import io.Printer;
import util.Response;

import java.io.File;
import java.io.Serializable;

public class SaveCommand implements Command, Serializable {
    private final Collection collection;
    private final Printer printer;

    public SaveCommand(Collection collection, Printer printer) {

        this.collection = collection;
        this.printer = printer;
    }

    @Override
    public Response execute(Object... args) {
        try {
            collection.save((JsonFile) args[0]);
            printer.println("The collection is saved to  " + args[0], ConsoleColor.HELP);
            printer.println(ClientApplication.SEPARATOR, ConsoleColor.ERROR);
        } catch (InputOutputException | NullPointerException | ArrayIndexOutOfBoundsException e) {
            throw new ExecutionException("You have not entered an JsonFile.");
        }
        return null;
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Saves the collection to  file";
    }

    @Override
    public Class<?>[] getArgumentsClasses() {
        return new Class[]{File.class};
    }
}
