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

            String op = in.readLine();
            switch (op) {
                case "set":
                    key = in.readLine();
                    value = in.readLine();
                    map.put(key, value);
                    out.println("Set Success.");
                    break;

                case "get":
                    key = in.readLine();
                    value = map.get(key);
                    out.println("Value for \"" + key + "\" is: " + value);
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

