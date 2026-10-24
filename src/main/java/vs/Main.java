package vs;

public class Main {

    public static void main(String[] args) {

        // Zwei Threads, da receive() blockiert und beide Sockets parallel laufen müssen
        new Thread(new SyslogReceiver(), "syslog").start();
        new Thread(new DiscoveryResponder(), "discovery").start();
    }
}