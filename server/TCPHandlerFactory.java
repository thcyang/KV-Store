import java.util.Map;

public class TCPHandlerFactory implements Runnable {
    private static TCPHandlerFactory ourInstance = new TCPHandlerFactory();
    private boolean listenning = true;
    private int port = 5556;
    private Map<String, String> map;
    private Socket socket;

    private TCPHandlerFactory() {
    }

    public static TCPHandlerFactory getInstance() {
        return ourInstance;
    }

    public void run() {
        // TODO
        // This while loop is responsible for establishing
        // new connection from different clients
        try {
            serverSocket = new ServerSocket(port);
        } 
        catch (IOException e) {
            System.err.println("Could not listen on the port.");
            System.exit(-1);
        }
        while (listenning) {
          try{
             socket s = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             String key = in.readLine();
             String value = in.readLine();
             map.put(key, value);
          }
          catch (IOException e){
            e.printStackTrace();
          }
        }
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}

