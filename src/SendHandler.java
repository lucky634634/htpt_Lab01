import java.io.PrintWriter;
import java.net.Socket;

public class SendHandler extends Thread {
    private ProcessInfo[] processInfos;

    public SendHandler(ProcessInfo[] processInfos) {
        this.processInfos = processInfos;
    }

    public void run() {
        for (int i = 0; i < processInfos.length; i++) {
        }
    }

    private void SendMessage(Socket socket, Message message) {
        try (PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
            pw.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
