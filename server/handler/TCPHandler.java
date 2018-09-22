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

            StringBuilder sb = null;
            String op = in.readLine();
            switch (op) {
                case "set":

                    count = Integer.parseInt(in.readLine());
                    for (i = 0; i < count; i++) {
                        key = in.readLine();
                        value = in.readLine();
                        set(key, value);
                    }
                    out.println("Set Success.");
                    break;

                case "get":
                    count = Integer.parseInt(in.readLine());
                    for (i = 0; i < count; i++) {
                        key = in.readLine();
                        value = get(key);
                        out.println("Value for \"" + key + "\" is: " + value);
                    }
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
