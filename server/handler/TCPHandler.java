package handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPHandler extends Handler implements Runnable {
    private Socket socket;

    public TCPHandler(LRUCache<String, String> lruCache, Socket socket) {
        super(lruCache);
        this.socket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String key = null;
            String value = null;
            int i, count;

            //String message = in.readLine();

            //String[] strs = message.split("\0");

            //
            //int num = Integer.parseInt(strs.length);
            /**
            for (i = 0; i < strs.length; i++) {
                System.out.println(strs[i]);
            }
            */
            //
            StringBuilder sb = null;
            //String op = strs[0];
            String op = in.readLine();
            switch (op) {
                case "set":
                    
                    count = Integer.parseInt(in.readLine());
                    for (i = 0; i < count; i++) {
                        key = in.readLine();
                        value = in.readLine();
                        set(key, value);
                    }
                    
                    //key = in.readLine();
                    //value = in.readLine();
                    //set(key, value);
                    out.println("Set Success.");
                    break;

                case "get":
                    //key = in.readLine();
                    //value = get(key);
                    
                    //sb = new StringBuilder(strs[1]);
                    count = Integer.parseInt(in.readLine());
                    for (i = 0; i < count; i++) {
                        //sb.append("\0Value for \"");
                        key = in.readLine();
                        //sb.append(key);
                        value = get(key);
                        //sb.append("\" is: ");
                        //sb.append(value);
                        out.println("Value for \""+key+"\" is: "+value);
                    }
                    //String mess = sb.toString();
                    
                    //out.println(mess);
                    //out.println("Value for \""+key+"\" is: "+value);
                    break;

                case "stats":
                    out.println("Count of objects currently stored in the KV store: " + stats());
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
