package util;

import commands.Command;
import file.JsonFile;
import io.Printer;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import static io.ConsoleColor.*;

public class UDPServer {

    private final int port;
    private DatagramSocket socket;
    private final InetAddress address;
    private final Map<String, Command> commandMap;
    private final Printer printer;
    private final static int BUFFER_SIZE = 65_535;
    private final Scanner sc;
    private final collection.Collection collection;
    private final Supplier<?> saveSupplier;
    private boolean wasExit = false;

    public UDPServer(int port, String address, Printer printer, Map<String, Command> commandMap, Scanner sc, collection.Collection collection, Supplier<?> saveSupplier) throws UnknownHostException {
        this.port = port;
        this.printer = printer;
        this.commandMap = commandMap;
        this.address = InetAddress.getByName(address);
        this.sc = sc;
        this.saveSupplier = saveSupplier;
        this.collection = collection;
    }


    public void connect() throws SocketException {
        try {
            socket = new DatagramSocket(port, address);
            printer.println("The connection is established", HELP);
        } catch (SocketException e) {
            throw new SocketException("The connection is not established");
        }
    }

    public void disconnect() {
        socket.close();
        printer.println("The server has shut down", REQUEST);
    }

    public void receive() {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            socket.receive(dp);
            Request request = Deserializer.deSerializeRequest(dp.getData());
            Response response = commandMap.get(request.getCommandName()).execute(request.getCommandArgs());
            send(response, dp.getAddress(), dp.getPort());
        } catch (IOException e) {
            printer.println("The received data could not be converted, the data was corrupted during transmission", ERROR);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            printer.println("The received data could not be converted, the classes of the received objects do not exist", ERROR);
        } catch (ClassCastException e) {
            printer.println("The received data could not be converted, objects of a different type were expected", ERROR);
        }
    }

    public void send(Response response, InetAddress address, int port) throws IOException {
        try {
            ByteBuffer byteBuffer = Serializer.serializeResponse(response);
            byte[] bufferToSend = byteBuffer.array();
            DatagramPacket datagramPacket = new DatagramPacket(bufferToSend, bufferToSend.length, address, port);
            socket.send(datagramPacket);
        } catch (SocketTimeoutException e) {
            printer.println("Failed to send data to the client, the client is not responding", ERROR);
        } catch (IOException e) {
            printer.println("Failed to send data to the client, connection problems", ERROR);
        }
    }

    public boolean wasExit() {
        return this.wasExit;
    }

    public void execute(Object obj) {
        if ("exit".equals(obj)) wasExit = true;
        try {
            printer.println(collection.save((JsonFile) saveSupplier.get()), HELP);
        } catch (Exception e) {
            printer.println(e.getMessage(), ERROR);
            execute(obj);
        }
    }

}
