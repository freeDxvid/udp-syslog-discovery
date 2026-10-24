package vs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class SyslogReceiver implements Runnable {

    public static final int MAX_LENGTH = 2048; //Standart für RFC5424
    public static final int PORT = 514; //SYSLOG Port

    public void run() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("[Syslog] Lausche auf UDP-Port " + PORT);
            
            byte[] buffer = new byte[MAX_LENGTH + 1];
            // +1 als Überlauf-Melder, wenn der letzte byte gesetzt ist, ist die Nachricht zu lang

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                //Clientinfos
                String client = packet.getAddress().getHostAddress() + ":" + packet.getPort();

                if (packet.getLength() > MAX_LENGTH) {
                    System.out.println("[Syslog] WARNUNG: Nachricht von " + client
                            + " zu lang (> " + MAX_LENGTH + " Bytes) - verworfen");
                    continue; //Zu lange Nachrichten werden hier verworfen
                }

                String text = new String(packet.getData(), 0, packet.getLength(),
                        StandardCharsets.UTF_8);
                System.out.println("[" + client + "] " + text);
            }
        } catch (IOException e) {
            System.err.println("[Syslog] Fehler: " + e.getMessage());
        }
    }
}