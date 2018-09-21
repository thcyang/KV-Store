import java.io.IOException;
import java.net.ServerSocket;
import handler.TCPHandler;

public class TCPHandlerFactory implements Runnable {
    private static TCPHandlerFactory ourInstance = new TCPHandlerFactory();
    private boolean listenning = true;
    private int port = 5556;

    private ServerSocket serverSocket;

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
            System.out.println("Waiting for connections for TCP.");
        } catch (IOException e) {
            System.err.println("Could not listen on the port.");
            System.exit(-1);
        }
        while (listenning) {
            try {
                TCPHandler tcp = new TCPHandler(serverSocket.accept());
                new Thread(tcp).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
