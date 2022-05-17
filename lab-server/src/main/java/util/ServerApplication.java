package util;

import collection.Collection;
import commands.*;
import file.FileManager;
import file.JsonFile;
import file.TextFile;
import io.InputData;
import io.Printer;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.ConsoleColor.ERROR;
import static io.ConsoleColor.REQUEST;

public class ServerApplication {
    public static final String SEPARATOR = "-----------------------";

    private final Scanner sc = new Scanner(System.in);
    private final Map<String, Command> commandsByName = new HashMap<>();
    private final Printer printer;
    private final String[] fileName;
    private final Collection collection;
    private Supplier<?> supplier;
    private UDPServer udpServer;

    public ServerApplication(String[] fileName, Collection collection, Printer printer) {
        this.fileName = fileName;
        this.collection = collection;
        this.printer = printer;
        fillCommands();
    }

    public void run() {
        try {
            String fileName = this.fileName[0];
            final File file = new File(fileName.trim());
            final TextFile textFile = new TextFile(file);
            final JsonFile jsonFile = new JsonFile(textFile);
            printer.println(collection.add(jsonFile.read()));
            connection();
            while (!udpServer.wasExit()) {
                Thread thread = new Thread(() -> {
                    while (!udpServer.wasExit()) {
                        udpServer.receive();
                    }
                    udpServer.disconnect();
                });
                thread.setDaemon(true);
                thread.start();
                String message = sc.nextLine();
                udpServer.execute(message);
            }
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
        InputData data = new InputData();
        commandsByName.put("help", new HelpCommand(new Printer(false), commandsByName));
        commandsByName.put("exit", new ExitCommand(new Printer(false)));
        commandsByName.put("info", new InfoCommand(collection, printer));
        commandsByName.put("show", new ShowCommand(collection, printer));
        commandsByName.put("add", new AddCommand(collection, printer));
        commandsByName.put("update", new UpdateIdCommand(collection, printer));
        commandsByName.put("remove_by_id", new RemoveByIdCommand(collection, printer));
        commandsByName.put("clear", new ClearCommand(collection, printer));
        commandsByName.put("add_if_max", new AddIfMaxCommand(collection, printer));
        commandsByName.put("remove_greater", new RemoveGreaterCommand(collection, printer));
        commandsByName.put("remove_lower", new RemoveLowerCommand(collection, printer));
        commandsByName.put("remove_all_by_weight", new RemoveAllByWeight(collection, printer));
        commandsByName.put("count_greater_than_killer", new CountGreaterThanKillerCommand(collection, printer));
        commandsByName.put("filter_greater_than_age", new FilterGreaterThanAgeCommand(collection, printer));
        supplier = () -> new FileManager(requestElement.get("Enter the file name:", sc, printer,
                data::getFileName, false), printer).getJsonFileByName();
    }

    private void connection() {
        try {
            printer.println("Enter the host name:", REQUEST);
            String hostName = sc.nextLine();
            printer.println("Enter the port:", REQUEST);
            int port = Integer.parseInt(sc.nextLine());
            udpServer = new UDPServer(port, hostName, printer, commandsByName, sc, collection, supplier);
            udpServer.connect();
        } catch (InputMismatchException | NumberFormatException | UnknownHostException e) {
            printer.println("You have entered the wrong port or host", ERROR);
            connection();
        } catch (SocketException e) {
            printer.println(e.getMessage(), ERROR);
            connection();
        }
    }

}
