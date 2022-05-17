package commands;


import com.google.gson.JsonParseException;
import execute.AdvancedScript;
import file.TextFile;
import io.JsonString;
import io.Printer;
import io.UDPClient;

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
    private final Map<String, Class<?>[]> commandArguments;
    private final Printer printer;

    public CommandArguments(String commandAsString, Map<String, Supplier<Object[]>> stringSupplierMap, Map<String, Class<?>[]> commandArgs, Printer printer) {
        this.commandAsString = commandAsString;
        this.stringSupplierMap = stringSupplierMap;
        this.commandArguments = commandArgs;
        this.printer = printer;
    }

    public CommandArguments(Map<String, Supplier<Object[]>> stringSupplierMap, Map<String, Class<?>[]> commandArgs, Printer printer, String... args) {
        this.args = args;
        this.stringSupplierMap = stringSupplierMap;
        this.commandArguments = commandArgs;
        this.printer = printer;
    }

    public CommandArguments(Map<String, Supplier<Object[]>> stringSupplierMap, Map<Class<?>,
            BiFunction<Scanner, Printer, Object>> requestMap,
                            Map<String, Class<?>[]> commandArgs, String commandAsString, Printer printer) {
        this.stringSupplierMap = stringSupplierMap;
        this.commandAsString = commandAsString;
        this.requestMap = requestMap;
        this.printer = printer;
        this.commandArguments = commandArgs;
    }

    public Object[] get() {
        return stringSupplierMap.get(commandAsString).get();
    }

    public Object[] get(ArrayList<String> data) {
        Class<?>[] commandArgsClasses = commandArguments.get(commandAsString);
        ArrayList<Object> commandArgs = new ArrayList<>();
        int start = 0;
        for (Class<?> arg : commandArgsClasses) {
            StringBuilder argAsString = new StringBuilder();
            JsonString jsonString = new JsonString();
            if ("execute_script".equals(commandAsString)) {
                try {
                    commandArgs.add(new AdvancedScript(new TextFile(new File(data.get(0).trim())), commandArguments, stringSupplierMap, requestMap, printer).execute((UDPClient) stringSupplierMap.get("script").get()[0]));
                    break;
                } catch (ClassCastException | JsonParseException | IOException ignored) {
                }
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
            throw new JsonParseException("You have entered incorrect arguments in the command " + commandAsString);
        }
        return commandArgs.toArray();
    }
}
