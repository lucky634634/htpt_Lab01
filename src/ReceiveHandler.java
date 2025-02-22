import java.io.*;
import java.net.*;

public class ReceiveHandler extends Thread {
    private Process _process = null;

    public ReceiveHandler(Process process) {
        this._process = process; 
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(_process.currentProcessInfo.port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> HandleIncomeMessage(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandleIncomeMessage(Socket socket) {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            Message message = (Message) ois.readObject();
            LogHandler.Log(message.toString(), _process.currentProcessInfo.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
