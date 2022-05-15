import util.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPServer {

    private final int port;
    private DatagramSocket socket;
    private final ServerApplication serverApplication;
    private final InetAddress address;
    private final static int BUFFER_SIZE = 65_535;

    public UDPServer(int port, ServerApplication serverApplication, String address) throws UnknownHostException {
        this.port = port;
        this.serverApplication = serverApplication;
        this.address = InetAddress.getByName(address);
    }

    public boolean connect() {
        try {
            socket = new DatagramSocket(port);
            return true;
        } catch (SocketException e) {
            return false;
        }
    }

    public void disconnect() {
        socket.close();
    }

    public void receive() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        socket.receive(dp);
        Request request = Deserializer.deSerializeRequest(dp.getData());
        Response response = serverApplication.getCommandsByName().get(request.getCommandName()).execute(request.getCommandArgs());
        System.out.println(response);
        send(response);
        System.out.println("was");
    }

    public void send(Response response) throws IOException {
        ByteBuffer byteBuffer = Serializer.serializeResponse(response);
        byte[] bufferToSend = byteBuffer.array();
        DatagramPacket datagramPacket = new DatagramPacket(bufferToSend, bufferToSend.length, address, 8989);
        socket.send(datagramPacket);
    }
}
