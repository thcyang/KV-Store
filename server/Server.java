import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;

import handler.LRUCache;

public class Server {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getLocalHost();

            // Get IP Address
            byte[] ipAddr = addr.getAddress();

            // Get hostname
            String hostname = addr.getHostAddress();
            System.out.println("Server IP = " + hostname);
        } catch (UnknownHostException e) {
        }
        LRUCache<String, String> lruCache = new LRUCache<>(20);

        TCPHandlerFactory t = TCPHandlerFactory.getInstance();
        t.setLruCache(lruCache);
        new Thread(t).start();

        UDPHandlerFactory u = UDPHandlerFactory.getInstance();
        u.setLruCache(lruCache);
        new Thread(u).start();
    }
}

