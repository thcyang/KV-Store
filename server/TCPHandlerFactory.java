import java.io.IOException;
import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;

import handler.LRUCache;
import handler.TCPHandler;

public class TCPHandlerFactory implements Runnable {
    private static TCPHandlerFactory ourInstance = new TCPHandlerFactory();
    private boolean listenning = true;
    private boolean bTesting = true;
    private int port = 5556;
    private LRUCache<String, String> lruCache;

    private ServerSocket serverSocket;

    private int count = 0;
    private int maxThroughput = 0;

    private TCPHandlerFactory() {
    }

    public static TCPHandlerFactory getInstance() {
	return ourInstance;
    }

    public void setLruCache(LRUCache<String, String> lruCache) {
	this.lruCache = lruCache;
    }

    public void run() {
	if (bTesting) {
	    TimerTask repeatedTask = new TimerTask() {
		public void run() {
		    if (count > maxThroughput) {
			maxThroughput = count;
			System.out.println("TCP maxThroughput: " + maxThroughput);
		    }
		    count = 0;
		}
	    };
	    Timer timer = new Timer("Timer");
	    long delay = 10L;
	    long period = 1000L;
	    timer.scheduleAtFixedRate(repeatedTask, delay, period);
	}
	try {
	    serverSocket = new ServerSocket(port);
	    System.out.println("Waiting for connections for TCP.");
	} catch (IOException e) {
	    System.err.println("Could not listen on the port.");
	    System.exit(-1);

	}
	while (listenning) {
	    try {
		TCPHandler tcp = new TCPHandler(lruCache, serverSocket.accept());
		count++;
		new Thread(tcp).start();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	try {
	    serverSocket.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
