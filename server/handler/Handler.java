package handler;

public abstract class Handler {
    private LRUCache<String, String> lruCache = new LRUCache<>(20);

    void set(String key, String value) {
        lruCache.set(key, value);
        lruCache.logPrint();
    }

    String get(String key) {
        lruCache.logPrint();
        return lruCache.get(key);
    }

    int stats() {
        return lruCache.size();
    }
}

