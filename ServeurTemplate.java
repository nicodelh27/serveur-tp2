import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ServeurTemplate {
    public static void main(String[] args) {

        try(DatagramSocket socket=new DatagramSocket(3005)) {

            byte[] message=new byte[10];
            ByteBuffer bb=ByteBuffer.wrap(message);
            bb.order(ByteOrder.BIG_ENDIAN);

            DatagramPacket packet = new DatagramPacket(message,message.length);

            socket.receive(packet);
            System.out.println("Requête de "+packet.getSocketAddress());

            bb.clear();
            int nombre1 = bb.getInt();
            int nombre2 = bb.getInt();

            bb.clear();
            bb.putInt(nombre1 + nombre2);
            packet.setLength(4);
            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
