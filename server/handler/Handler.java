package handler;

public abstract class Handler {
    private LRUCache<String, String> lruCache;

    public Handler(LRUCache<String, String> lruCache) {
        this.lruCache = lruCache;
    }

    void set(String key, String value) {
        lruCache.set(key, value);
        lruCache.logPrint();
    }

    String get(String key) {
        String str = lruCache.get(key);
        lruCache.logPrint();
        return str;
    }

    int stats() {
        return lruCache.size();
    }
}

