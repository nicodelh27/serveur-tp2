import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class ClientCalcul {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in)) {
            final int serverPort = 3005;
            InetAddress serverAddress = InetAddress.getByName("localhost");
            byte[] message = new byte[10];
            ByteBuffer bb = ByteBuffer.wrap(message);
            bb.order(ByteOrder.BIG_ENDIAN);
            DatagramPacket packet = new DatagramPacket(message, message.length);

            while (true) {
                System.out.println("Entrez un calcul : ");
                String calcul = scanner.nextLine();
                String[] elements = calcul.split(" ");
                if (elements.length != 3) {
                    System.out.println("Calcul invalide");
                    continue;
                }
                try {
                    int nombre1 = Integer.parseInt(elements[0]);
                    int nombre2 = Integer.parseInt(elements[2]);
                    char operateur = elements[1].charAt(0);
                    System.out.println(nombre1 + " " + operateur + " " + nombre2);

                    bb.clear();
                    packet.setLength(10);
                    bb.putInt(nombre1);
                    bb.putInt(nombre2);
                    bb.putChar(operateur);
                    packet.setAddress(serverAddress);
                    packet.setPort(serverPort);
                    socket.send(packet);

                    bb.clear();
                    socket.receive(packet);
                    bb.clear();
                    int resultat = bb.getInt();
                    System.out.println("Résultat: " + resultat);

                } catch (NumberFormatException ex) {
                    System.out.println("Calcul invalide");
                }
            }

        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
