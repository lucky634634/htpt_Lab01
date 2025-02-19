import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ChildProcess {
    private static int port;
    private static String ipAddr = " ";
    private static int numOfMsgsPerSec = 0;
    public static void main(String[] args) {
        int processId = Integer.parseInt(args[0]);

        // Doc file config.properties de lay cac thong so cua chuong trinh
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        port = Integer.parseInt(properties.getProperty("port"));
        ipAddr = properties.getProperty("ipAddr");
        numOfMsgsPerSec = Integer.parseInt(properties.getProperty("numOfMsgsPerSec"));

        // Bat dau chay server de nhan tin nhan
        Thread t = new Thread(() -> startServer(processId));
        t.start();
        System.out.println("Process " + processId + " started");

        // Tao cac thread de gui tin nhan
        ExecutorService executor = Executors.newFixedThreadPool(11);

        // Semaphore de kiem soat viec gui tin nhan
        Semaphore semaphore = new Semaphore(1);

        // Gui tin nhan
        for (int i = 1; i <= 12; i++) {
            if (i != processId) {
                final int targetProcess = i;
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < numOfMsgsPerSec; j++) {
                            semaphore.acquire(); // Chiem huu semaphore
                            try {
                                sendMessage(ipAddr, targetProcess, "Message " + j + " from process " + processId);
                                System.out.println("Process " + processId + " sent message " + j + " to process " + targetProcess);
                            } finally {
                                semaphore.release(); // Giai phong semaphore
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executor.shutdown();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Process " + processId + " terminated");
    }

    private static void startServer(int processId) {
        try (ServerSocket serverSocket = new ServerSocket(port + processId)) {
            System.out.println("Process " + processId + " listening on port " + (port + processId));
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleIncomingMessage(socket, processId)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleIncomingMessage(Socket socket, int processId) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = input.readLine()) != null) {
                System.out.println("Process " + processId + " received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String peerIP, int targetProcess, String message) {
        try (Socket socket = new Socket(peerIP, port+targetProcess);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
            output.println(message);
        } catch (IOException e) {
            System.out.println("Failed to send message to " + peerIP);
        }
    }
}