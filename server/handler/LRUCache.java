package handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This Class is for the eviction policy (LRU)
 * */
public class LRUCache<KeyType, ValueType> {
    private Map<KeyType, DLNode> map = new ConcurrentHashMap<>();
    private DLNode head;
    private DLNode end;
    private int capacity;
    private int len;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        len = 0;
    }

    public int size() {
        return len;
    }

    public ValueType get(KeyType key) {
        if (map.containsKey(key)) {
            DLNode latest = map.get(key);
            removeNode(latest);
            setHead(latest);
            return latest.val;
        } else {
            return null;
        }
    }

    public void removeNode(DLNode node) {
        DLNode cur = node;
        DLNode pre = cur.pre;
        DLNode post = cur.next;

        if (pre != null) {
            pre.next = post;
        } else {
            head = post;
        }

        if (post != null) {
            post.pre = pre;
        } else {
            end = pre;
        }
    }

    public void setHead(DLNode node) {
        node.next = head;
        node.pre = null;
        if (head != null) {
            head.pre = node;
        }

        head = node;
        if (end == null) {
            end = node;
        }
    }

    public void set(KeyType key, ValueType value) {
        if (map.containsKey(key)) {
            DLNode oldNode = map.get(key);
            oldNode.val = value;
            removeNode(oldNode);
            setHead(oldNode);
        } else {
            DLNode newNode = new DLNode(key, value);
            if (len < capacity) {
                setHead(newNode);
                map.put(key, newNode);
                len++;
            } else {
                map.remove(end.key);
                end = end.pre;
                if (end != null) {
                    end.next = null;
                }

                setHead(newNode);
                map.put(key, newNode);
            }
        }
    }

    public void logPrint() {
        DLNode cur = head;
        while (cur != null) {
            System.out.println("\r\nKeyVal: " + cur.key + cur.val);
            cur = cur.next;
        }
        System.out.println("\r\n");
    }

    class DLNode {
        public KeyType key;
        public ValueType val;

        public DLNode pre;
        public DLNode next;

        public DLNode(KeyType key, ValueType value) {
            val = value;
            this.key = key;
        }
    }
}
