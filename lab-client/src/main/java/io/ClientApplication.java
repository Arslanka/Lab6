package io;

import com.google.gson.JsonParseException;
import data.Coordinates;
import data.Dragon;
import data.Location;
import data.Person;
import exceptions.InvalidCommandNameException;
import exceptions.RecursiveCallException;
import execute.CommandInterpreter;
import file.FileManager;
import io.request.*;
import util.Request;
import util.Response;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static io.ConsoleColor.*;

public class ClientApplication implements Serializable {
    public static final String SEPARATOR = "-----------------------";

    private final Scanner sc = new Scanner(System.in);
    private final Map<String, Supplier<Object[]>> supplierMap = new HashMap<>();
    private final Map<Class<?>, BiFunction<Scanner, Printer, Object>> requestMap = new HashMap<>();
    private final Printer printer;

    public ClientApplication(Printer printer) {
        this.printer = printer;
        fillCommands();
    }

    public void startInteractiveMode() {
        try {
            CommandInterpreter commandInterpreter = new CommandInterpreter(supplierMap, this.printer, requestMap);
            ClientSocket clientSocket = new ClientSocket();
            boolean running = true;
            while (running) {
                printer.println(("Enter the command:"), REQUEST);
                try {
                    Request request = (commandInterpreter.run(new ArrayList<>(Arrays.asList(sc.nextLine().trim().split("\\s+")))));
                    running = !request.wasExit();
                    clientSocket.send(request);
                    Response response = clientSocket.receive();
                    System.out.println(response.getMessage());
                } catch (NoSuchElementException e) {
                    running = false;
                } catch (RecursiveCallException | InvalidCommandNameException | IllegalArgumentException | JsonParseException e) {
                    printer.println(e.getMessage(), ERROR);
                }
            }
            printer.println("Client's program execution stopped", CONSOLE);
        } catch (NoSuchElementException e) {
            printer.println("The program ended incorrectly. Please restart the program.", ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.println("You didn't specify the file name to populate the collection", ERROR);
        } catch (Exception e) {
            printer.println(e.getMessage(), ERROR);
        }
    }

    public void fillCommands() {
        RequestElement requestElement = new RequestElement();
        InputData inputData = new InputData();
        supplierMap.put("help",
                () -> new Object[]{});
        supplierMap.put("exit",
                () -> new Object[]{});
        supplierMap.put("info",
                () -> new Object[]{});
        supplierMap.put("show",
                () -> new Object[]{});
        supplierMap.put("add",
                () -> new Object[]{new RequestDragon(requestElement, sc, printer, inputData).get().build()});
        //supplierMap.put("update",
        //        () -> new Object[]{collection.validatedId(requestElement.get("Enter id:", sc, printer, inputData::getId, true)),  //todo two requests
        //                new RequestDragon(requestElement, sc, printer, inputData).get().build()});
        supplierMap.put("remove_by_id",
                () -> new Object[]{requestElement.get("Enter id:", sc, printer, inputData::getId, true)});
        supplierMap.put("clear",
                () -> new Object[]{});
        supplierMap.put("save",
                () -> new Object[]{new FileManager(requestElement
                        .get("Enter the file name:", sc, printer, inputData::getFileName, false), printer)
                        .getJsonFileByName()});
//        commandsByName.put("execute_script", new ExecuteScriptCommand());
//        supplierMap.put("execute_script",
//                () -> new Object[]{new AdvancedScript(
//                        new FileManager(requestElement
//                                .get("Enter the name of the script:", sc, printer, inputData::getFileName, false), printer)
//                                .getTextFileByName(), commandsByName, supplierMap, requestMap, printer)});
        supplierMap.put("add_if_max",
                () -> new Object[]{new RequestDragon(requestElement, sc, printer, inputData).get().build()});
        supplierMap.put("remove_greater",
                () -> new Object[]{new RequestDragon(requestElement, sc, printer, inputData).get().build()});
        supplierMap.put("remove_lower",
                () -> new Object[]{new RequestDragon(requestElement, sc, printer, inputData).get().build()});
        supplierMap.put("remove_all_by_weight",
                () -> new Object[]{requestElement.get("Enter the weight:", sc, printer, inputData::getWeight, true)});
        supplierMap.put("count_greater_than_killer",
                () -> new Object[]{new RequestPerson(requestElement, sc, printer, inputData).get().build()});
        supplierMap.put("filter_greater_than_age",
                () -> new Object[]{requestElement.get("Enter the age:", sc, printer, inputData::getAge, true)});
        requestMap.put(Dragon.class, (Scanner scanner, Printer printer) -> new RequestDragon(requestElement, scanner, printer, inputData).get().build());
        requestMap.put(Person.class, (Scanner scanner, Printer printer) -> new RequestPerson(requestElement, scanner, printer, inputData).get().build());
        requestMap.put(Coordinates.class, (Scanner scanner, Printer printer) -> new RequestCoordinates(requestElement, scanner, printer, inputData).get().build());
        requestMap.put(Location.class, (Scanner scanner, Printer printer) -> new RequestLocation(requestElement, scanner, printer, inputData).get().build());
    }
}