package io;

import util.Deserializer;
import util.Request;
import util.Response;
import util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static io.ConsoleColor.ERROR;
import static io.ConsoleColor.HELP;

public class UDPClient {

    private String hostname;
    private int port;
    private InetSocketAddress address;
    private DatagramChannel datagramChannel;
    private boolean isAvailable = false;
    private final Printer printer;
    private final static String LOCALHOST = "localhost";
    private final static int BUFFER_SIZE = 65_535,
            TIMES_TO_TRY_READ = 20,
            READ_ATTEMPT_DELAY_MS = 500;

    public UDPClient(int port, Printer printer) {
        this.port = port;
        this.printer = printer;
    }

    public UDPClient(String hostname, int port, Printer printer) {
        this.hostname = hostname;
        this.port = port;
        this.printer = printer;
    }

    public void connect() {
        try {
            if (hostname == null) hostname = LOCALHOST;
            datagramChannel = DatagramChannel.open();
            address = new InetSocketAddress(hostname, port);
            datagramChannel.bind(new InetSocketAddress(0));
            datagramChannel.configureBlocking(false);
            if (address.isUnresolved()) {
                printer.println(ERROR.wrapped("The address " + hostname + " does not exist, please specify another address"));
                return;
            }
        } catch (SocketException e) {
            printer.println(ERROR.wrapped("The specified connection port is busy, specify another port"));
        } catch (IOException e) {
            printer.println(ERROR.wrapped("Something went wrong when connecting to the server"));
        }
        isAvailable = true;
        printer.println("You have successfully connected to the host " + hostname + " with port " + port, HELP);
    }

    public void disconnect() {
        if (!isAvailable) return;
        try {
            datagramChannel.close();
        } catch (IOException e) {
            printer.println(ERROR.wrapped("Something went wrong while disconnecting from the server"));
        }
        isAvailable = false;
    }

    public Response send(Request request) {
        try {
            datagramChannel.send(Serializer.serializeRequest(request), address);
        } catch (IOException e) {
            return new Response(ERROR.wrapped("No response from the server"));
        }
        return receive();
    }

    public Response receive() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        boolean dataReceived = false;
        try {
            for (int i = 0; i < TIMES_TO_TRY_READ; i++) {
                if (datagramChannel.receive(buffer) != null) {
                    dataReceived = true;
                    break;
                }
                Thread.sleep(READ_ATTEMPT_DELAY_MS);
            }
        } catch (IOException e) {
            return new Response(ERROR.wrapped("Unstable connection"));
        } catch (InterruptedException e) {
            return new Response(ERROR.wrapped("Data receiving was interrupted"));
        }
        if (!dataReceived)
            return new Response(ERROR.wrapped("The server is not responding"));
        try {
            return Deserializer.deSerializeResponse(buffer.array());
        } catch (IOException e) {
            return new Response(ERROR.wrapped("The data was corrupted"));
        } catch (ClassNotFoundException e) {
            return new Response(ERROR.wrapped("The result could not be converted, the required class does not exist"));
        } catch (ClassCastException e) {
            e.printStackTrace();
            return new Response(ERROR.wrapped("The result could not be converted, an object of a different type was expected"));
        }
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
