package handler;

import java.util.Map;

public class UDPHandler extends Handler implements Runnable {
    private Map<String, String> map;

    public UDPHandler(Map<String, String> map) {
        this.map = map;
    }

    public void run() {

    }
}

