package commands;


import com.google.gson.JsonParseException;
import file.JsonFile;
import file.TextFile;
import io.JsonString;
import io.Printer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Supplier;


public class CommandArguments implements Serializable {
    private String commandAsString;
    private String[] args;
    private final Map<String, Supplier<Object[]>> stringSupplierMap;
    private Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap;
    private Printer printer;
    private Command command;

    public CommandArguments(String commandAsString, Map<String, Supplier<Object[]>> stringSupplierMap) {
        this.commandAsString = commandAsString;
        this.stringSupplierMap = stringSupplierMap;
    }

    public CommandArguments(Map<String, Supplier<Object[]>> stringSupplierMap, String... args) {
        this.args = args;
        this.stringSupplierMap = stringSupplierMap;
    }

    public CommandArguments(Map<String, Supplier<Object[]>> stringSupplierMap, Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap, String commandAsString) {
        this.stringSupplierMap = stringSupplierMap;
        this.commandAsString = commandAsString;
        this.requestMap = requestMap;
    }

    public Object[] get() {
        return stringSupplierMap.get(commandAsString).get();
    }

    public Object[] get(ArrayList<String> data) {
        Class<?>[] commandArgsClasses = command.getArgumentsClasses();
        ArrayList<Object> commandArgs = new ArrayList<>();
        int start = 0;
        for (Class<?> arg : commandArgsClasses) {
            StringBuilder argAsString = new StringBuilder();
            JsonString jsonString = new JsonString();
            if (command instanceof SaveCommand) { //todo fix instanceof + open/closed
                try {
                    commandArgs.add(new JsonFile(new TextFile(new File(data.get(0).trim()))));
                    break;
                } catch (ClassCastException | JsonParseException | IOException ignored) {
                }
//            } else if (command instanceof ExecuteScriptCommand) {
//                try {
//                    commandArgs.add(new AdvancedScript(new TextFile(new File(data.get(0).trim())), commandsByName, stringSupplierMap, requestMap, printer));
//                    break;
//                } catch (ClassCastException | JsonParseException | IOException ignored) {
//                }
            } else {
                for (; start < data.size(); ++start) {
                    argAsString.append(data.get(start)).append('\n');
                    try {
                        Object obj = jsonString.read(argAsString.toString(), arg);
                        commandArgs.add(obj);
                        ++start;
                        break;
                    } catch (Exception ignored) {
                    }
                    try {
                        Object obj = (requestMap.get(arg)).apply(new Scanner(argAsString.toString()), new Printer(true));
                        commandArgs.add(obj);
                        break;
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        if (commandArgs.size() != commandArgsClasses.length && !data.toString().isEmpty()) {
            throw new JsonParseException("You have entered incorrect arguments in the command " + command.getName());
        }
        return commandArgs.toArray();
    }

}
