package handler;

import java.util.Map;

public class TCPHandler extends Handler implements Runnable {
    private Map<String, String> map;

    public TCPHandler(Map<String, String> map) {
        this.map = map;
    }

    public void run() {

    }
}

