//package execute;
//
//import com.google.gson.JsonParseException;
//import commands.Command;
//import commands.CommandArguments;
//import exceptions.ExecutionException;
//import exceptions.InputOutputException;
//import exceptions.RecursiveCallException;
//import execute.CommandInterpreter;
//import file.TextFile;
//import io.Printer;
//
//import java.io.File;
//import java.util.*;
//import java.util.function.BiFunction;
//import java.util.function.Supplier;
//
//
//public class AdvancedScript {
//    public static final Set<File> executableScripts = new HashSet<>();
//    private final TextFile textFile;
//    private final Map<String, Command> commandMap;
//    private final Map<String, Supplier<Object[]>> supplierMap;
//    private final Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap;
//
//    private final Printer printer;
//    private boolean noExit = true;
//
//    public AdvancedScript(TextFile textFile, Map<String, Command> commandMap,
//                          Map<String, Supplier<Object[]>> supplierMap, Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap,
//                          Printer printer) {
//        this.textFile = textFile;
//        this.commandMap = commandMap;
//        this.supplierMap = supplierMap;
//        this.printer = printer;
//        this.requestMap = requestMap;
//    }
//
//    public boolean execute() {
//        try {
//            if (executableScripts.contains(textFile.getFile())) {
//                executableScripts.clear();
//                throw new RecursiveCallException(textFile.getFile().toString());
//            }
//            executableScripts.add(textFile.getFile());
//            ArrayList<String> stringRep = new ArrayList<>();
//            try {
//                Arrays.stream(textFile.read().split("\\s+")).filter(s -> !s.isEmpty()).forEach(stringRep::add);
//            } catch (InputOutputException e) {
//                executableScripts.remove(textFile.getFile());
//                throw new ExecutionException(e.getMessage());
//            }
//            ArrayList<String> data = new ArrayList<>();
//            String lastCommand = "";
//            CommandInterpreter commandInterpreter = new CommandInterpreter(supplierMap, printer, requestMap);
//            boolean isFirst = true;
//            for (String s : stringRep) {
//                if (!noExit) {
//                    executableScripts.remove(this.textFile.getFile());
//                    return false;
//                }
//                if (!commandMap.containsKey(s)) {
//                    data.add(s);
//                    continue;
//                }
//                if (isFirst) {
//                    if (!data.isEmpty()) {
//                        executableScripts.remove(this.textFile.getFile());
//                        throw new ExecutionException("You have entered incorrect data for the script");
//                    }
//                    lastCommand = s;
//                    isFirst = false;
//                    continue;
//                }
//                interpreter(data, lastCommand, commandInterpreter);
//                data.clear();
//                lastCommand = s;
//            }
//            if (!noExit) {
//                executableScripts.remove(this.textFile.getFile());
//                return false;
//            } else if (lastCommand.isEmpty()) {
//                executableScripts.remove(this.textFile.getFile());
//                throw new ExecutionException("The command name was entered incorrectly");
//            }
//            interpreter(data, lastCommand, commandInterpreter);
//            executableScripts.remove(this.textFile.getFile());
//            return true;
//        } catch (NullPointerException e) {
//            executableScripts.remove(this.textFile.getFile());
//            throw new ExecutionException("You have entered incorrect data for the script");
//        } catch (JsonParseException | InputOutputException e) {
//            executableScripts.remove(this.textFile.getFile());
//            throw new ExecutionException("You have entered incorrect data for the script\n" + e.getMessage());
//        }
//    }
//
//    private void interpreter(ArrayList<String> data, String lastCommand, CommandInterpreter commandInterpreter) {
//        ArrayList<String> list = new ArrayList<>();
//        if (!commandMap.get(lastCommand).withArgument()) {
//            if (!data.isEmpty()) {
//                executableScripts.remove(this.textFile.getFile());
//                throw new ExecutionException("You have entered incorrect data for the script");
//            }
//            list.add(lastCommand);
//            noExit = commandInterpreter.run(list);
//        } else {
//            list.add(lastCommand);
//            if (data.isEmpty()) commandInterpreter.run(list);
//            else {
//                noExit = commandInterpreter.run(list,
//                        new CommandArguments(supplierMap, requestMap, lastCommand).get(data));
//            }
//        }
//    }
//}
//
//
