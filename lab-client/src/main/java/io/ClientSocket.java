package io;

import util.Deserializer;
import util.Request;
import util.Response;
import util.Serializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class ClientSocket {
    private final int defaultPort = 10214;
    private final int selectorDelay = 100;
    private Selector selector;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private int port = defaultPort;

    public ClientSocket(int aPort) throws IOException {
        initialization(aPort);
    }

    public ClientSocket() throws IOException {
        initialization(this.defaultPort);
    }

    private void initialization(int aPort) throws IOException {
        datagramChannel = DatagramChannel.open();
        selector = Selector.open();
//        datagramChannel.socket().bind(new InetSocketAddress(aPort));
//        datagramChannel.configureBlocking(false);
//        datagramChannel.register(selector, SelectionKey.OP_READ);
    }

    public void send(Request request) throws IOException {
        ByteBuffer buffer = Serializer.serializeRequest(request);
        datagramChannel.send(buffer, socketAddress);
    }

    public Response receive() throws IOException, ClassNotFoundException {
        if (selector.select(selectorDelay) == 0) {
            return null;
        }
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isReadable()) {
                int arraySize = datagramChannel.socket().getReceiveBufferSize();
                ByteBuffer packet = ByteBuffer.allocate(arraySize);
                socketAddress = datagramChannel.receive(packet);
                ((Buffer) packet).flip();
                byte[] bytes = new byte[packet.remaining()];
                packet.get(bytes);
                return Deserializer.deSerializeResponse(bytes);
            }
        }
        return null;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void stop() throws IOException {
        selector.close();
        datagramChannel.close();
    }
}
