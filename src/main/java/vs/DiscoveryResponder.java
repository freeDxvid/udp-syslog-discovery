package vs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DiscoveryResponder implements Runnable {

    public static final int PORT = 8888;

    public void run() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("[Discovery] Lausche auf UDP-Port " + PORT);
            byte[] buffer = new byte[512]; //reservierter Speicher für die UDP-Nutzlast

            while (true) {
                // leeren Container fürs UDP-Paket erstellen
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request); //wartet auf empfangene Paket auf dem Port

                // Leere Antwort direkt an den Absender um die IP des Servers an den Client zu senden
                DatagramPacket response = new DatagramPacket(
                        new byte[0], 0, request.getAddress(), request.getPort());
                socket.send(response);

                System.out.println("[Discovery] Anfrage von "
                        + request.getAddress().getHostAddress() + ":" + request.getPort()
                        + " beantwortet");
            }
        } catch (IOException e) {
            System.err.println("[Discovery] Fehler: " + e.getMessage());
        }
    }
}