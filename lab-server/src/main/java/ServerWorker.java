import collection.Collection;

import java.io.IOException;
import java.net.*;

public class ServerWorker {
    private static final int PORT = 8989;

    private final String[] fileName;
    private final Collection collection;

    public ServerWorker(String[] fileName, Collection collection) {
        this.fileName = fileName;
        this.collection = collection;
    }

    public void start() throws IOException {
        //            String fileName = this.fileName[0];
//            final JsonFile jsonFile = new JsonFile(new TextFile(new File(fileName.trim())));
//            collection.add(jsonFile.read());
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Connection was established");
        byte[] arr = new byte[1024];
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(arr, arr.length, address, PORT);
        socket.receive(packet);
        System.out.println(new String(packet.getData()));

    }


}
