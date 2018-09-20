package handler;

import java.util.Map;
import java.io.*;
import java.net.*;

public class TCPHandler extends Handler implements Runnable {
    private Map<String, String> map;
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
            System.out.println(strs[i]);
          //
            String[] strs = message.split("\\r?\\n");
            
            //
            int num = Integer.parseInt(strs.length);
            for (i=0;i<num;i++) {
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
                    }
                    out.println("Set Success.");
                    break;

                case "get":
                    count = Integer.parseInt(strs[1]);
                    for(i=0; i<count; i++) {
                        sb.append("\\r?\\n value for \"");
                        key = strs[2+i];
                        sb.append(key);
                        value = map.get(key);
                        sb.append("\" is: ");
                        sb.append(value);
                        sb.append("\\r?\\n");
                        message = sb.toString;
                    }
                    out.println(sb);
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
}

