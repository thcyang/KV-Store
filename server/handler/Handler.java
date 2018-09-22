package handler;

public abstract class Handler {
    private LRUCache<String, String> lruCache;

    public Handler(LRUCache<String, String> lruCache) {
	this.lruCache = lruCache;
    }

    void set(String key, String value) {
	lruCache.set(key, value);
    }

    String get(String key) {
	String str = lruCache.get(key);
	return str;
    }

    int stats() {
	return lruCache.size();
    }
}
