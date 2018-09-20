import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<String, String>();
        TCPHandlerFactory.getInstance().setMap(map);
        UDPHandlerFactory.getInstance().setMap(map);

        new Thread(TCPHandlerFactory.getInstance()).start();
        new Thread(UDPHandlerFactory.getInstance()).start();
    }
}

