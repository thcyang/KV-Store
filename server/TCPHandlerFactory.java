import java.util.Map;
import java.net.*;
import java.io.*;
import handler.TCPHandler;

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
              try {
                 TCPHandler tcp = new TCPHandler(map, serverSocket.accept());
                 new Thread(tcp).start();  
              }
              catch (IOException e) {
                 e.printStackTrace();
              }
        }
        try {
              serverSocket.close();
        } 
        catch (IOException e) {
              e.printStackTrace();
        }
    }
    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
  

