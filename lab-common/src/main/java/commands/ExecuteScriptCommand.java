//package commands;
//
//import exceptions.ExecutionException;
//import exceptions.InputOutputException;
//import execute.AdvancedScript;
//
//import java.io.File;
//
//public class ExecuteScriptCommand implements Command {
//
//    @Override
//    public boolean execute(Object... args) {
//        try {
//            AdvancedScript script = (AdvancedScript) args[0];
//            return script.execute();
//        } catch (InputOutputException | ExecutionException e) {
//            throw new ExecutionException("Error executing the execute_script command\n" + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean withArgument() {
//        return true;
//    }
//
//    @Override
//    public String getName() {
//        return "execute_script";
//    }
//
//    @Override
//    public String getDescription() {
//        return "Reads and executes the script from the specified file.";
//    }
//
//    @Override
//    public Class<?>[] getArgumentsClasses() {
//        return new Class[]{File.class};
//    }
//}
