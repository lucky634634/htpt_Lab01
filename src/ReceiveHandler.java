import java.io.*;
import java.net.*;

public class ReceiveHandler extends Thread {
    public int port;

    public ReceiveHandler(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> HandleIncomeMessage(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandleIncomeMessage(Socket socket) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message = br.readLine();
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
