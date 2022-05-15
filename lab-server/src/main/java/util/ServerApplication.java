package util;

import collection.Collection;
import commands.*;
import data.Coordinates;
import data.Dragon;
import data.Location;
import data.Person;
import file.FileManager;
import io.InputData;
import io.Printer;
import io.request.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ServerApplication {
    public static final String SEPARATOR = "-----------------------";

    private final Scanner sc = new Scanner(System.in);
    private final Map<String, Command> commandsByName = new HashMap<>();
    private Printer printer;
    private String[] fileName;
    private Collection collection;

    public ServerApplication(String[] fileName, Collection collection, Printer printer) {
        this.fileName = fileName;
        this.collection = collection;
        this.printer = printer;
        fillCommands();
    }


    public void fillCommands() {
        RequestElement requestElement = new RequestElement();
        InputData inputData = new InputData();
        commandsByName.put("help", new HelpCommand(new Printer(false), commandsByName));
        commandsByName.put("exit", new ExitCommand(new Printer(false)));
        commandsByName.put("info", new InfoCommand(collection, printer));
        commandsByName.put("show", new ShowCommand(collection, printer));
        commandsByName.put("add", new AddCommand(collection, printer));
        commandsByName.put("update", new UpdateIdCommand(collection, printer));
        commandsByName.put("remove_by_id", new RemoveByIdCommand(collection, printer));
        commandsByName.put("clear", new ClearCommand(collection, printer));
        commandsByName.put("save", new SaveCommand(collection, printer));
//        commandsByName.put("execute_script", new ExecuteScriptCommand());
//        supplierMap.put("execute_script",
//                () -> new Object[]{new AdvancedScript(
//                        new FileManager(requestElement
//                                .get("Enter the name of the script:", sc, printer, inputData::getFileName, false), printer)
//                                .getTextFileByName(), commandsByName, supplierMap, requestMap, printer)});
        commandsByName.put("add_if_max", new AddIfMaxCommand(collection, printer));
        commandsByName.put("remove_greater", new RemoveGreaterCommand(collection, printer));
        commandsByName.put("remove_lower", new RemoveLowerCommand(collection, printer));
        commandsByName.put("remove_all_by_weight", new RemoveAllByWeight(collection, printer));
        commandsByName.put("count_greater_than_killer", new CountGreaterThanKillerCommand(collection, printer));
        commandsByName.put("filter_greater_than_age", new FilterGreaterThanAgeCommand(collection, printer));
    }

    public Map<String, Command> getCommandsByName() {
        return commandsByName;
    }

}
