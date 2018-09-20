import java.util.Map;
import java.net.*;
import java.io.*;

import handler.UDPHandler;
import handler.LRUCache;

public class UDPHandlerFactory implements Runnable {
    private static UDPHandlerFactory ourInstance = new UDPHandlerFactory();
    private boolean listenning = true;
    private int port = 5555;
    private Map<String, String> map;
    LRUCache<String, String> lruCache;
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
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on the port.");
            System.exit(-1);
        }
        while (listenning) {
            try {
                byte[] me = new byte[1024 * 64];
                DatagramPacket packet = new DatagramPacket(me, me.length);
                socket.receive(packet);
                UDPHandler udp = new UDPHandler(map, packet);
                new Thread(udp).start();

                /****
                 byte[] o = new byte[1024];
                 byte[] buf = null;
                 DatagramPacket packet4op = new DatagramPacket(o, o.length);
                 socket.receive(packet4op);
                 String op = new String(packet4op.getData(), 0, packet4op.getLength());
                 InetAddress addr = packet4op.getAddress();
                 int portnum = packet4op.getPort();

                 switch (op){
                 case "set":
                 byte[] buf4k = new byte[1024];
                 byte[] buf4v = new byte[1024];
                 DatagramPacket packet4k = new DatagramPacket(buf4k, buf4k.length);
                 DatagramPacket packet4v = new DatagramPacket(buf4v, buf4v.length);
                 socket.receive(packet4k);
                 socket.receive(packet4v);
                 String key = new String(packet4k.getData(), 0, packet4k.getLength());
                 String value = new String(packet4v.getData(), 0, packet4v.getLength());
                 map.put(key,value);
                 buf = "Set Success.".getBytes();
                 DatagramPacket pack = new DatagramPacket(buf, buf.length, addr, portnum);
                 socket.send(pack);
                 break;

                 case "get":
                 byte[] buf4g = new byte[1024];
                 DatagramPacket packet4g = new DatagramPacket(buf4g, buf4g.length);
                 socket.receive(packet4g);
                 String key4g = new String(packet4g.getData(), 0, packet4g.getLength());
                 String v = map.get(key4g);
                 String s = "Value for \"" +key4g+ "\" is \"" +v+"\".";
                 buf = s.getBytes();
                 DatagramPacket pack2 = new DatagramPacket(buf, buf.length, addr, portnum);
                 socket.send(pack2);
                 break;

                 case "stats":
                 String str = "Count of objects currently stored in the KV store: "+ map.size();
                 buf = str.getBytes();
                 DatagramPacket pack3 = new DatagramPacket(buf, buf.length, addr, portnum);
                 socket.send(pack3);
                 break;
                 }
                 ******/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    public void setLruCache(LRUCache<String, String> lruCache) {
	this.lruCache = lruCache;
    }
    
}
