import java.util.Map;
import java.net.*;
import java.io.*;

public class UDPHandlerFactory implements Runnable {
    private static UDPHandlerFactory ourInstance = new UDPHandlerFactory();
    private boolean listenning = true;
    private int port = 5555;
    private Map<String, String> map;
    private DatagramSocket socket;

    private UDPHandlerFactory() {
    }

    public static UDPHandlerFactory getInstance() {
        return ourInstance;
    }

    public void run() {
        // TODO
        // This while loop is responsible for establishing
        // new connection from different clients
        try {
             Socket = new DatagramSocket(port);
        } 
        catch (IOException e) {
             System.err.println("Could not listen on the port.");
             System.exit(-1);
        }
        while (listenning) {
            try{
                byte[] buf4k = new byte[1024];
                byte[] buf4v = new byte[1024];
                DatagramPacket packet4k = new DatagramPacket(buf4k, buf4k.length);
                DatagramPacket packet4v = new DatagramPacket(buf4v, buf4v.length);
                socket.receive(packet4k);
                socket.receive(packet4v);
                String key = new String(packet4k.getData(), 0, packet4k.getLength());
                String value = new String(packet4v.getData(), 0, packet4v.getLength());
                map.put(key,value);
            }
          catch(IOExceptin e){
                e.printStackTrace();
          } 
        }
      
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}

