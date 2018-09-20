package handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.io.*;
import java.net.*;

public class TCPHandler extends Handler implements Runnable {
    private Map<String, String> map;
    LRUCache<String, String> lruCache;

    private Socket socket;

    public TCPHandler(Map<String, String> map, Socket socket) {
        this.map = map;
        this.socket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Got connection from " + socket.getInetAddress());
            String key = null;
            String value = null;
            int i,count;
          
            String message = in.readLine();
          //
          //  System.out.println(strs[i]);
          //
            String[] strs = message.split("\0");
            
            //
            //int num = Integer.parseInt(strs.length);
            for (i=0;i< strs.length;i++) {
                System.out.println(strs[i]);
            }
            //
          
            StringBuilder sb = new StringBuilder(strs[1]);
            String op = strs[0];
            switch (op) {
                case "set":
                    count = Integer.parseInt(strs[1]);
                    for(i=0; i<count; i++) {
                        key = strs[2+2*i];
                        value = strs[3+2*i];
                        map.put(key, value);
		  		//		lruCache.set(key, value);

                    }
                    out.println("Set Success.");
                    break;

                case "get":
                    count = Integer.parseInt(strs[1]);
                    for(i=0; i<count; i++) {
                        sb.append("\0Value for \"");
                        key = strs[2+i];
                        sb.append(key);
                        value = map.get(key);
					//    value = lruCache.get(key);
                        sb.append("\" is: ");
                        sb.append(value);
                        sb.append("\r\n");  
                    }
                    String mess = sb.toString();
                    out.println(mess);
                    break;

                case "stats":
                    out.println("Count of objects currently stored in the KV store: " + map.size());
                    break;
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLruCache(LRUCache<String, String> lruCache) {
	this.lruCache = lruCache;
    }
}
