import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;

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
    int portnum4U = 5555;
    int portnum4T = 5556;

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
        }

        setHost(args[0]);
        setOp(args[2]);

        // Stores the operation as well as keys and/or values in the list in order
        List<String> list = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            list.add(args[i]);
        }

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
         Socket socket = new Socket(host,portnum4T);
         PrintWriter out = new PrintWriter(s.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
         String str = null;
         out.println(op);
         switch(op) {
           case "set":
                out.println(list.get(0));
                out.println(list.get(1));
                str = in.readLine();
                System.out.println(str);
                break;
           
           case "get":
                out.println(list.get(0));
                str = in.readLine();
                System.out.println(str);
                break;
           
           case "stats":
                str = in.readLine();
                System.out.println(str);
                break;
         }     
         socket.close();
       }
       catch (IOException){
          System.err.println("Sending Failed!");
       }
    }

    private static void sendByUDP() {
        try {
          DatagramSocket socket = new DatagramSocket();
          byte[] o = op.getBytes();
          byte[] buf = new byte[1024];
          DatagramPacket packet4op = new DatagramPacket(o, o.length, InetAddress.getByName(host), portnum4U);
          DatagramPacket rece = new DatagramPacket(buf, buf.length);
          String str = null;
          socket.send(packet4op);
          
          switch (op){
            case "set":
               byte[] key = list.get(0).getBytes();//byte[] key = pairs.get(0).getKey().getBytes();
               byte[] value = list.get(1).getBytes();
               DatagramPacket packet4k = new DatagramPacket(key, key.length, InetAddress.getByName(host), portnum4U);
               DatagramPacket packet4v = new DatagramPacket(value, value.length, InetAddress.getByName(host), portnum4U);
               socket.send(packet4k);
               socket.send(packet4v);
               socket.receive(rece);
               str = new String(rece.getData(), 0 ,rece.getLength());
               System.out.println(str);
               break;
              
           case "get":
                byte[] key4g = list.get(0).getBytes();
                DatagramPacket packet4g = new DatagramPacket(key4g, key4g.length, InetAddress.getByName(host), portnum4U);
                socket.send(packet4g);
                socket.receive(rece);
                str = new String(rece.getData(), 0 ,rece.getLength());
                System.out.println(str);
                break;
           
           case "stats":
                socket.receive(rece);
                str = new String(rece.getData(), 0 ,rece.getLength());
                System.out.println(str);
                break;   
          }
          
          socket.close();
        }
        catch (IOException){
          System.err.println("Sending Failed!");
        }
    }

    private static void showUsage() {

        return;
    }

    private static String genMessage() {

    }
}

