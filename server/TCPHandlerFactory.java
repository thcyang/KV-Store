import java.util.Map;

public class TCPHandlerFactory implements Runnable {
    private static TCPHandlerFactory ourInstance = new TCPHandlerFactory();
    private boolean listenning = true;
    private int port = 5555;
    private Map<String, String> map;

    private TCPHandlerFactory() {
    }

    public static TCPHandlerFactory getInstance() {
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

