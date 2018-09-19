import java.util.Map;
import java.net.*;
import java.io.*;

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
             PrintWriter out = new PrintWriter(s.getOutputStream(),true);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             System.out.println("Got connection from " + s.getInetAddress());
             String key = null;
             String value = null;
             
             String op = in.readLine();
             switch(op) {
               case "set":
                  key = in.readLine();
                  value = in.readLine();
                  map.put(key, value);
                  out.println("Set Success.");
                  break;
              
               case "get":
                  key = in.readLine();
                  value = map.get(key);
                  out.println("Value for \"" +key+ "\" is \"" +value+"\".");
                  break;
               
               case "stats":
                  out.println("Count of objects currently stored in the KV store: "+ map.size());
                  break;
               }
            
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

