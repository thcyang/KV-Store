import java.net.*;
import java.io.*;


public class TestClient {
    private static String host;
    private static String op;
    private static int portnum4T = 5556;
    private static String message;

    private static void setHost(String host) {
        TestClient.host = host;
    }

    private static void setOp(String op) {
        TestClient.op = op.toLowerCase();
    }

    /**
     * java client localhost --TCP set latency
     * java client localhost --TCP get latency
     *
     * java client localhost --TCP set key value
     * */
    public static void main(String[] args) {
        setHost(args[0]);
        setOp(args[2]);
        switch (args[3]) {
            case "latency":
                latency(args);
                break;
            case "throughput":
                throughput(args);
                break;
            default:
                showUsage();
        }
    }

    private static void latency(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            genMessage(randMessage(args));
            sendByTCP();
        }
        long end = System.currentTimeMillis();
        int avg = (int) (end - start) / 10000;
        System.out.println("The average latency is " + avg + " ms.");
    }

    private static String[] randMessage(String[] args) {
        String[] newArgs = new String[5];
        newArgs[0] = args[0];
        newArgs[1] = args[1];
        newArgs[2] = args[2];
        newArgs[3] = genKey();
        newArgs[4] = genVal();
        return newArgs;
    }

    private static String genKey() {
        String[] pool = {"j", "e", "c", "9", "c"};
        int randIndex = (int) (Math.random() * 10) % 5;
        int randFold = (int) (Math.random() * 64);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < randFold; i++) {
            sb.append(pool[randIndex]);
        }
        return sb.toString();
    }

    private static String genVal() {
        String[] pool = {"jfei2", "e94jf", "chfk3", "930fk", "chbei"};
        int randIndex = (int) (Math.random() * 10) % 5;
        int randFold = (int) (Math.random() * 100);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < randFold; i++) {
            sb.append(pool[randIndex]);
        }
        return sb.toString();
    }

    private static void throughput(String[] args) {
        genMessage(randMessage(args));
        while (true) {
            new Thread(TestClient::sendByTCP).start();
        }
    }

    private static void sendByTCP() {
        String[] strs = message.split("\0");
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
                    //out.println(message);
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
                    //out.println(message);
                    //answer = in.readLine();
                    //analyAnswer(answer);
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

    private static void showUsage() {
        String s = "usage: java TestClient <Server> <-t | --TCP> <operation> <test name>" + System.lineSeparator() + System.lineSeparator() +
                "<protocol> should always be -t or --TCP, since only TCP uses socket which we are required to test." + System.lineSeparator() +
                "<test name> could either be latency or throughput.";
        System.out.println(s);
        System.out.println();
        System.out.println();
    }
}

