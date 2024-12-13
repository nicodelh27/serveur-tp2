import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ServeurTemps {


    public static void main(String[] args) {

        try {
            DatagramSocket socket=new DatagramSocket(2005);
            byte[] message=new byte[10];
            ByteBuffer bb=ByteBuffer.wrap(message);
            bb.order(ByteOrder.BIG_ENDIAN);
            DatagramPacket packet=new DatagramPacket(message,message.length);

            while(true) {
                socket.receive(packet);
                System.out.println("Requête de "+packet.getSocketAddress());

                int time=(int)(System.currentTimeMillis()/1000);
                bb.clear();
                bb.putInt(time);
                packet.setLength(4);

                socket.send(packet);

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
