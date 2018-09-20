import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;
import handler.LRUCache;

public class Server {
    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<String, String>();
      
        try {
                        InetAddress addr = InetAddress.getLocalHost();

                        // Get IP Address
                        byte[] ipAddr = addr.getAddress();

                        // Get hostname
                        String hostname = addr.getHostAddress();
                        System.out.println("Server IP = " + hostname);
             } catch (UnknownHostException e) {}
      
        LRUCache<String, String> lruCache = new LRUCache<String, String>(5);
        TCPHandlerFactory.getInstance().setLruCache(lruCache);
        TCPHandlerFactory.getInstance().setMap(map);
        UDPHandlerFactory.getInstance().setMap(map);

        new Thread(TCPHandlerFactory.getInstance()).start();
        new Thread(UDPHandlerFactory.getInstance()).start();
    }
}

