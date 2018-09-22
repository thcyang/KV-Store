import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;
import java.util.TimerTask;

import handler.LRUCache;
import handler.UDPHandler;
//Create thread for UDP
public class UDPHandlerFactory implements Runnable {
    private static UDPHandlerFactory ourInstance = new UDPHandlerFactory();
    private boolean listenning = true;
    private boolean bTesting = true;
    private int port = 5555;
    private DatagramSocket socket;
    private LRUCache<String, String> lruCache;
    private int count = 0;
    private int maxThroughput = 0;

    private UDPHandlerFactory() {
    }

    public static UDPHandlerFactory getInstance() {
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
	// This while loop is responsible for establishing
	// new connection from different clients
	try {
	    socket = new DatagramSocket(port);
	} catch (IOException e) {
	    System.err.println("Could not listen on the port.");
	    System.exit(-1);
	}
	while (listenning) {
	    try {
		byte[] me = new byte[1024 * 64];
		DatagramPacket packet = new DatagramPacket(me, me.length);
		socket.receive(packet);
		UDPHandler udp = new UDPHandler(lruCache, packet);
		new Thread(udp).start();
		count++;
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

    }
}
