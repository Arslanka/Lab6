package execute;

import commands.Command;
import commands.CommandArguments;
import exceptions.*;
import io.Printer;
import util.Request;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static io.ConsoleColor.ERROR;

public class CommandInterpreter {
    private final Printer printer;
    private final Map<String, Supplier<Object[]>> supplierMap;
    private final Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap;

    public CommandInterpreter(Map<String, Supplier<Object[]>> supplierMap,
                              Printer printer, Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap) {
        this.printer = printer;
        this.supplierMap = supplierMap;
        this.requestMap = requestMap;
    }


    public Request run(ArrayList<String> commandWithArgs, Object... scriptArgs) { //todo change command.execute to request and response with ser/des
        try {
            if (!supplierMap.containsKey(commandWithArgs.get(0).toLowerCase())) {
                throw new InvalidCommandNameException("Command with the name " + commandWithArgs.get(0) + " does not exist.");
            }
            String commandName = commandWithArgs.get(0).toLowerCase();
            if (scriptArgs.length == 0 && commandWithArgs.size() == 1) {
                return new Request(commandName, new CommandArguments(commandWithArgs.get(0).toLowerCase(), supplierMap).get());
            } else if (scriptArgs.length == 0) {
                commandWithArgs.remove(0);
                return new Request(commandName, new CommandArguments(supplierMap, requestMap, commandName).get(commandWithArgs));
            } else
                return new Request(scriptArgs);
        } catch (ExistingIdException | InputOutputException | IncorrectIdException | ObjectBuildException | ExecutionException e) {
            printer.println(e.getMessage(), ERROR);
        }
        return null;
    }
}
