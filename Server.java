import java.io.*;
import java.net.*;

class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // Constructor....
    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is Ready to Accept the connection");
            System.out.println("Waiting.....");
            socket = server.accept();

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
                        System.out.println("Client Terminate the chat...");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);

                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection Closed...");
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
                System.out.println("Connection Closed...s");
            }
        };

        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is Server : Let's Start with Server ");
        new Server();
    }
}