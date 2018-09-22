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
    private static String host;
    private static String op;
    private static int portnum4U = 5555;
    private static int portnum4T = 5556;
    private static String message;
    private static final String Separator = "\r\n";

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
            case "set":
                if (args.length % 2 != 1) {
                    showUsage();
                    return;
                }
                break;
            case "get":
                if (args.length < 4) {
                    showUsage();
                    return;
                }
                break;
            case "stats":
                if (args.length != 3) {
                    showUsage();
                    return;
                }
                break;
            default:
                showUsage();
                return;
        }

        setHost(args[0]);
        setOp(args[2]);

        //generate the message form used for trasfering information between client and server.
        genMessage(args);

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
        // Establish connection for TCP.
    private static void sendByTCP() {
        String[] strs = message.split("\\r\\n");
        try {
            Socket socket = new Socket(host, portnum4T);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String answer = null;
            int count,i;
            out.println(strs[0]);
            switch (op) {
                case "set":
                    out.println(strs[1]);
                    count = Integer.parseInt(strs[1]);
                    for (i = 0; i < count; i++) {
                        out.println(strs[2 + 2 * i]);
                        out.println(strs[3 + 2 * i]);
                    }
                    answer = in.readLine();
                    System.out.println(answer);
                    break;

                case "get":
                    out.println(strs[1]);
                    count = Integer.parseInt(strs[1]);
                    for (i = 0; i < count; i++) {
                        out.println(strs[2 + i]);
                    }
                    for (i = 0; i < count; i++) {
                        answer = in.readLine();
                        System.out.println(answer);
                    }
                    break;

                case "stats":
                    out.println(message);
                    answer = in.readLine();
                    System.out.println(answer);
                    break;
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("Sending Failed!");
        }
    }
        // Send and receive packet by UDP
    private static void sendByUDP() {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] me = message.getBytes();
            byte[] buf = new byte[1024 * 64];
            DatagramPacket packet = new DatagramPacket(me, me.length, InetAddress.getByName(host), portnum4U);
            DatagramPacket rece = new DatagramPacket(buf, buf.length);
            socket.send(packet);
            socket.receive(rece);
            String str = new String(rece.getData(), 0, rece.getLength());

            if (op.equals("get")) {
                analyAnswer(str);
            } else {
                System.out.println(str);
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Sending Failed!");
        }
    }

    private static void showUsage() {
        String s = "usage: java Client <server> <protocol> <operation> <key> <value>" + System.lineSeparator() + System.lineSeparator() +
                "<server>" + System.lineSeparator() +
                "should be the IP address of the server" + System.lineSeparator() + System.lineSeparator() +
                "<protocol>" + System.lineSeparator() +
                "should be \"--TCP\" or \"-t\" or \"--UDP\" or \"-u\"" + System.lineSeparator() + System.lineSeparator() +
                "<operation>" + System.lineSeparator() +
                "should be \"SET\" or \"GET\" or \"STATS\"" + System.lineSeparator() + System.lineSeparator() +
                "<key>" + System.lineSeparator() +
                "should be a continuous string without any space or carriage return. There can be multiple keys." + System.lineSeparator() + System.lineSeparator() +
                "<value>" + System.lineSeparator() +
                "should be a continuous string without any space or carriage return. There can be multiple vaules corresponding to keys, and the format should be <key> followed by <value>, followed by next <key>, followed by next <value>, and so forth.";
        System.out.println(s);
    }

    private static void genMessage(String[] args) {
        StringBuilder sb = new StringBuilder(args[2]);
        int count, i;
        switch (args[2]) {
            case "set":
                count = (args.length - 3) / 2;
                sb.append(Separator);
                sb.append(count);
                for (i = 0; i < count; i++) {
                    sb.append(Separator);
                    sb.append(args[3 + 2 * i]);
                    sb.append(Separator);
                    sb.append(args[4 + 2 * i]);
                }
                sb.append(Separator);
                message = sb.toString();
                break;
            case "get":
                count = (args.length - 3);
                sb.append(Separator);
                sb.append(count);
                for (i = 0; i < count; i++) {
                    sb.append(Separator);
                    sb.append(args[3 + i]);
                }
                sb.append(Separator);
                message = sb.toString();
                break;
            case "stats":
                message = sb.toString();
                break;
            default:
                System.out.println("Something wrong about generating message!");
        }
    }

    private static void analyAnswer(String answer) {
        int count, i;
        String[] ansStrs = answer.split(Separator);
        count = Integer.parseInt(ansStrs[0]);
        for (i = 1; i <= count; i++) {
            System.out.println(ansStrs[i]);
        }
    }
}

