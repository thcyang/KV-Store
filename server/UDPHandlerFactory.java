import java.util.Map;

public class UDPHandlerFactory implements Runnable {
    private static UDPHandlerFactory ourInstance = new UDPHandlerFactory();
    private boolean listenning = true;
    private int port = 5555;
    private Map<String, String> map;

    private UDPHandlerFactory() {
    }

    public static UDPHandlerFactory getInstance() {
        return ourInstance;
    }

    public void run() {
        // TODO
        // This while loop is responsible for establishing
        // new connection from different clients
        while (listenning) {

        }
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}

