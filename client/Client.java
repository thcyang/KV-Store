import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;

class Pair {
    private String key;
    private String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
        System.out.println("Pair Constructor");
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}


/*
 * client usage: java client <server> <protocol> <operation> <key> <value>...
 * it supports multiple pairs of key and value
 *
 * protocol should be:
 *   --TCP  uses TCP
 *   --UDP  uses UDP
 *   -t     uses TCP
 *   -u     uses UDP
 *
 * operation should be:
 *   set    adds a key and value to the server
 *   get    returns a value to the client for the requested key
 *   stats  returns a count of objects currently stored in the KV store
 * */
public class Client {

    private static List<Pair> pairs = new ArrayList<>();
    private static String host;
    private static String op;
    int portnum = 5555;
    int portnum2 = 5556;

    private static void setHost(String host) {
        Client.host = host;
    }

    private static void setOp(String op) {
        Client.op = op;
    }

    public static void main(String[] args) {

        // Checks the arguments user passed
        if (args.length < 3) {
            showUsage();
            return;
        }

        // converts first 3 args to lower case
        for (int i = 0; i < 3; i++) {
            args[i] = args[i].toLowerCase();
        }

        // Checks the arguments user passed
        switch (args[2]) {
            case "stats":
            case "set":
                if (args.length % 2 != 1)
                    showUsage();
                break;
            case "get":
                if (args.length % 2 != 0)
                    showUsage();
                break;
            default:
                showUsage();
                return;
        }

        setHost(args[0]);
        setOp(args[2]);

        // Stores the keys and values in the list in order
        List<String> list = new ArrayList<>();
        for (int i = 3; i < args.length; i++) {
            list.add(args[i]);
        }

        // Generates Pair objects and store them in  List<Pair> pairs
        genPairs(list);

        // Determines which protocol will be used to transport data
        switch (args[1]) {
            case "--tcp":
            case "-t":
                sendByTCP();
                break;
            case "--udp":
            case "-u":
                sendByUDP();
                break;
            default:
                showUsage();
        }
    }

    private static void sendByTCP() {
       try{
         Socket socket = new Socket(host,portnum2);
         PrintWriter out = new PrintWriter(s.getOutputStream(), true);
         out.println(list.get(0));
         out.println(list.get(1));
         socket.close();
       }
       catch (IOException){
          System.err.println("Sending Failed!");
       }
    }

    private static void sendByUDP() {
        try {
          DatagramSocket socket = new DatagramSocket();
          byte[] key = list.get(0).getBytes();//byte[] key = pairs.get(0).getKey().getBytes();
          byte[] value = list.get(1).getBytes();
          DatagramPacket packet4k = new DatagramPacket (key, key.length, InetAddress.getByName(host), portnum);
          DatagramPacket packet4v = new DatagramPacket (value, value.length, InetAddress.getByName(host), portnum);
          socket.send(packet4k);
          socket.send(packet4v);
          socket.close();
        }
        catch (IOException){
          System.err.println("Sending Failed!");
        }
    }

    private static void genPairs(List<String> list) {
         Iterator it = list.iterator();
         while(it.hasNext()){
           String key = it.next();
           if(it.hasNext()){
             String value = it.next();
             pairs.add(new Pair(key, value));
           }
         }
    }

    private static void showUsage() {

    }
}

