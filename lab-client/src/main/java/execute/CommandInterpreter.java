package execute;

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
    private final Map<String, Class<?>[]> commandArgs;


    public CommandInterpreter(Map<String, Supplier<Object[]>> supplierMap,
                              Printer printer, Map<Class<?>, BiFunction<Scanner,
            Printer, Object>> requestMap, Map<String, Class<?>[]> commandArgs) {
        this.printer = printer;
        this.supplierMap = supplierMap;
        this.requestMap = requestMap;
        this.commandArgs = commandArgs;
    }


    public Request run(ArrayList<String> commandWithArgs, Object... scriptArgs) { //todo change command.execute to request and response with ser/des
        try {
            if (!supplierMap.containsKey(commandWithArgs.get(0).toLowerCase().trim())) {
                throw new InvalidCommandNameException("Command with the name " + commandWithArgs.get(0) + " does not exist.");
            }
            String commandName = commandWithArgs.get(0).toLowerCase().trim();
            if ("execute_script".equals(commandName)) {
                if (commandWithArgs.size() != 1) {
                    commandWithArgs.remove(0);
                    return new Request("", new CommandArguments(supplierMap, requestMap, commandArgs, commandName, printer).get(commandWithArgs));
                } else
                    return new Request("", new CommandArguments(commandWithArgs.get(0).toLowerCase(), supplierMap, commandArgs, printer).get());
            }
            if (scriptArgs.length == 0 && commandWithArgs.size() == 1) {
                return new Request(commandName, new CommandArguments(commandWithArgs.get(0).toLowerCase(), supplierMap, commandArgs, printer).get());
            } else if (scriptArgs.length == 0) {
                commandWithArgs.remove(0);
                return new Request(commandName, new CommandArguments(supplierMap, requestMap, commandArgs, commandName, printer).get(commandWithArgs));
            } else
                return new Request(commandName, scriptArgs);
        } catch (ExistingIdException | InputOutputException | IncorrectIdException | ObjectBuildException | ExecutionException e) {
            printer.println(e.getMessage(), ERROR);
        }
        return new Request("");
    }

}
