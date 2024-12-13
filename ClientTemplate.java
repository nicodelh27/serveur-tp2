import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class ClientTemplate {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in)) {

            // Initialisation des moyens de communication
            final int serverPort = 3005;
            final InetAddress serverAddress = InetAddress.getByName("localhost");

            // Initialisation du buffer
            byte[] message = new byte[10];
            ByteBuffer bb = ByteBuffer.wrap(message);
            bb.order(ByteOrder.BIG_ENDIAN);

            // Création du paquet
            DatagramPacket packet = new DatagramPacket(message, message.length);

            // Input de l'utilisateur
            System.out.println("Entrez un calcul : ");
            String calcul = scanner.nextLine();

            // Envoyer un paquet
            bb.clear();
            packet.setLength(10);
            bb.putInt(1);
            bb.putInt(2);
            packet.setAddress(serverAddress);
            packet.setPort(serverPort);
            socket.send(packet);

            // Recevoir un paquet
            socket.receive(packet);
            bb.clear();
            int resultat = bb.getInt();
            System.out.println("Résultat: " + resultat);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

