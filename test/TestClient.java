import java.net.*;
import java.io.*;

public class TestClient {
    private static String host;
    private static String op;
    private static int portnum4U = 5555;
    private static int portnum4T = 5556;
    private static String message;

    private static void setHost(String host) {
        TestClient.host = host;
    }

    private static void setOp(String op) {
        TestClient.op = op;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            int rand = (int) (Math.random() * 500);
            args[3] = rand + "";
            // all code previously in method main() was moved into method test()
            test(args);
        }
        System.out.println("Time of running client 10000 times: " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void test(String[] args) {
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

    private static void sendByTCP() {
        //String[] strs = message.split("\\r?\\n");
        try {
            Socket socket = new Socket(host, portnum4T);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String answer = null;
            //out.println(strs[0]);
            switch (op) {
                case "set":
                    //out.println(strs[1]);
                    //out.println(strs[2]);
                    out.println(message);
                    answer = in.readLine();
                    System.out.println(answer);
                    break;

                case "get":
                    //out.println(strs[1]);
                    out.println(message);
                    answer = in.readLine();
                    analyAnswer(answer);
                    //System.out.println(answer);
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


    /*message String form:
       set: operation+"\\r?\\n"+count+"\\r?\\n"+key+"\\r?\\n"+value+"\\r?\\n"+key+....
       get: operation+"\\r?\\n"+count+"\\r?\\n"+key+"\\r?\\n"+key+....
       stats: operation+"\\r?\\n"
    */
    private static void genMessage(String[] args) {
        StringBuilder sb = new StringBuilder(args[2]);
        int count, i;
        switch (args[2]) {
            case "set":
                count = (args.length - 3) / 2;
                sb.append("\0");
                sb.append(count);
                for (i = 0; i < count; i++) {
                    sb.append("\0");
                    sb.append(args[3 + 2 * i]);
                    sb.append("\0");
                    sb.append(args[4 + 2 * i]);
                }
                sb.append("\0");
                message = sb.toString();
                break;
            case "get":
                count = (args.length - 3);
                sb.append("\0");
                sb.append(count);
                for (i = 0; i < count; i++) {
                    sb.append("\0");
                    sb.append(args[3 + i]);
                }
                sb.append("\0");
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
        String[] strs = answer.split("\0");
        count = Integer.parseInt(strs[0]);
        for (i = 1; i <= count; i++) {
            System.out.println(strs[i]);
        }
    }
}

