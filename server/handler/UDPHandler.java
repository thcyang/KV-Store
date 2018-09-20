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
        try {
            InetAddress addr = packet.getAddress();
            int portnum = packet.getPort();
            int i,count;
            String message = new String(packet.getData(), 0, packet.getLength());
            String[] strs = message.split("\0");
            String key = strs[1];
            String value = strs[2];
            byte[] buf = null;
            DatagramSocket socket = new DatagramSocket();
  
            switch (strs[0]) {
                case "set":
                    count = Integer.parseInt(strs[1]);
                    for(i=0; i<count; i++) {
                        key = strs[2+2*i];
                        value = strs[3+2*i];
                        map.put(key, value);
                    }
                    buf = "Set Success.".getBytes();
                    DatagramPacket pack = new DatagramPacket(buf, buf.length, addr, portnum);
                    socket.send(pack);
                    break;

                case "get":
                    count = Integer.parseInt(strs[1]);
                    StringBuilder sb = new StringBuilder(strs[1]);
                    for(i=0; i<count; i++) {
                        sb.append("\0Value for \"");
                        key = strs[2+i];
                        sb.append(key);
                        value = map.get(key);
                        sb.append("\" is: ");
                        sb.append(value); 
                    }
                    String mess = sb.toString();
                    buf = mess.getBytes();
                    DatagramPacket pack2 = new DatagramPacket(buf, buf.length, addr, portnum);
                    socket.send(pack2);
                    break;

                case "stats":
                    String str = "Count of objects currently stored in the KV store: " + map.size();
                    buf = str.getBytes();
                    DatagramPacket pack3 = new DatagramPacket(buf, buf.length, addr, portnum);
                    socket.send(pack3);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

