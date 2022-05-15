package commands;

import exceptions.ExecutionException;
import exceptions.ExistingIdException;
import util.Response;

public interface Command{
    Response execute(Object... args) throws ExecutionException, ExistingIdException;

    boolean withArgument();

    String getName();

    String getDescription();

    Class<?>[] getArgumentsClasses();
}
