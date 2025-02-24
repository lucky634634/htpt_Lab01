import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class ReceiveHandler extends Thread {
    private Process _process = null;
    private Queue<Message> _bufferQueue = new LinkedList<>();

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
            _process.semaphore.acquireUninterruptibly();
            SES(message);
            _process.PrintTimeStamp();
            _process.semaphore.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SES(Message message) {
        try {
            if (message.IsVpEmpty()) {
                _process.IncreaseTimestamp(_process.currentProcessInfo.id);
                _process.UpdateTimestamp(message.timeStamp);
                LogHandler.Log(message.toString(), _process.logFile);
                return;
            }
            if (!message.ContainsPi(_process.currentProcessInfo.id)) {
                _process.IncreaseTimestamp(_process.currentProcessInfo.id);
                _process.UpdateTimestamp(message.timeStamp);
                LogHandler.Log(message.toString(), _process.logFile);
                return;
            }
            if (!TimeStampGreaterThan(message.timeStamp, _process.GetTimestamp())) {
                _process.IncreaseTimestamp(_process.currentProcessInfo.id);
                String log = "Buffer: " + message.toString();
                LogHandler.Log(log, _process.logFile);
                return;
            }
            _process.IncreaseTimestamp(_process.currentProcessInfo.id);
            _process.UpdateTimestamp(message.timeStamp);
            LogHandler.Log(message.toString(), _process.logFile);
        } catch (Exception e) {
        }
    }

    private boolean TimeStampGreaterThan(int[] t1, int[] t2) {
        for (int i = 0; i < t1.length; i++) {
            if (t1[i] <= t2[i]) {
                return false;
            }
        }
        return true;
    }

}
