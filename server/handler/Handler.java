package handler;

import java.util.Map;

public abstract class Handler {
    private Map<String, String> map;

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    void set(String key, String value) {
        map.put(key, value);
    }

    String get(String key) {
        return map.get(key);
    }

    int stats() {
        return map.size();
    }
}

