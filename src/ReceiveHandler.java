import java.io.*;
import java.net.*;
import java.util.Iterator;
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
            _process.semaphore.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SES(Message message) {
        if (CheckDelivery(message)) {
            LogHandler.Log("Delivered: " + message.message + " from " + message.fromId, _process.logFile);
            _process.MergeV_P(message.v_p);
            return;
        }
        LogHandler.Log("Buffer: " + message.message + " from " + message.fromId, _process.logFile);
        _bufferQueue.add(message);
        Iterator<Message> iterator = _bufferQueue.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            if (CheckDelivery(msg)) {
                LogHandler.Log("Get from buffer: " + msg.message + " from " + msg.fromId, _process.logFile);
                _process.MergeV_P(msg.v_p);
                iterator.remove();
            }
        }
    }

    private boolean CheckDelivery(Message message) {
        if (_process.v_p[message.fromId][message.toId] != message.v_p[message.fromId][message.toId] - 1) {
            return false;
        }
        for (int i = 0; i < message.v_p.length; i++) {
            if (i == message.fromId) {
                continue;
            }
            for (int j = 0; j < message.v_p.length; j++) {
                if (_process.v_p[i][j] < message.v_p[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
