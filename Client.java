import java.io.*;
import java.net.*;

class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {
            System.out.println("Sending Request to the Server..");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection Done...");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        // getting the data from client with the help of thread
        Runnable r1 = () -> {
            System.out.println("Reader Starting...");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server Terminate the chat...");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);

                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection Closed ...");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer Started....");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection Closed ...");
            }
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is Client");
        new Client();
    }
}