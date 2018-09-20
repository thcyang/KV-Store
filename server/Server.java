import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;

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
      
        TCPHandlerFactory.getInstance().setMap(map);
        UDPHandlerFactory.getInstance().setMap(map);

        new Thread(TCPHandlerFactory.getInstance()).start();
        new Thread(UDPHandlerFactory.getInstance()).start();
    }
}

