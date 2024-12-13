import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ClientTemps {
    public static void main(String[] args) {
        try {
            final int serverPort = 2005;
            InetAddress serverAddress = InetAddress.getByName("localhost");

            DatagramSocket socket = new DatagramSocket();
            byte[] message = new byte[10];

            ByteBuffer bb = ByteBuffer.wrap(message);
            bb.order(ByteOrder.BIG_ENDIAN);

            DatagramPacket packet = new DatagramPacket(message, message.length);
            packet.setLength(0);
            packet.setAddress(serverAddress);
            packet.setPort(serverPort);
            socket.send(packet);

            do {
                packet.setLength(message.length);
                socket.receive(packet);
            } while (!packet.getAddress().equals(serverAddress) || packet.getPort() != serverPort || packet.getLength() != 4);

            bb.clear();
            int time = bb.getInt();
            System.out.println("Temps: " + time);


        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
