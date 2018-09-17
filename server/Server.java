import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>();
        TCPHandlerFactory.getInstance().setMap(map);
        UDPHandlerFactory.getInstance().setMap(map);

        new Thread(TCPHandlerFactory.getInstance());
        new Thread(UDPHandlerFactory.getInstance());
    }
}

