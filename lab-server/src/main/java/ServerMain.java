import collection.Collection;
import io.Printer;
import util.ServerApplication;

import java.io.IOException;
import java.util.Scanner;

public class ServerMain {
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        ServerWorker serverWorker = new ServerWorker(args, new Collection());
//        serverWorker.start();
        ServerApplication application = new ServerApplication(new String[]{}, new Collection(), new Printer(false));
        UDPServer udpServer = new UDPServer(10214, application, "localhost");
        udpServer.connect();
        udpServer.receive();
    }
}
