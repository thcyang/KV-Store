package handler;

import java.util.Map;
import java.net.*;
import java.io.*;

public class UDPHandler extends Handler implements Runnable {
    private Map<String, String> map;
    private DatagramPacket packet;

    public UDPHandler(Map<String, String> map, DatagramPacket packet) {
        this.map = map;
        this.packet = packet;
    }

    public void run() {
        try{
          InetAddress addr = packet.getAddress();
          int portnum = packet.getPort();
          String message = new String(packet.getData(), 0, packet.getLength());
          String[] strs = message.split("\\r?\\n");
          byte[] buf = null;
          DatagramSocket socket = new DatagramSocket();
          
          switch (strs[0]){
                    case "set":
                        String key = strs[1];
                        String value = strs[2];
                        map.put(key,value);
                        buf = "Set Success.".getBytes();
                        DatagramPacket pack = new DatagramPacket(buf, buf.length, addr, portnum);
                        socket.send(pack);
                        break;

                    case "get":
                        String key4g = strs[1];
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
        catch (IOException e){
          e.printStackTrace();
        }
    }
}

