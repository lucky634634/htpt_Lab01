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
        System.out.println("Process");
        _process.PrintV_P();
        System.out.println("Message");
        message.PrintV_P();
        if (CheckDelivery(message)) {
            LogHandler.Log("Delivered: " + message.message + " from " + message.fromId, _process.logFile);
            _process.MergeV_P(message.v_p);
            System.out.println("Process");
            _process.PrintV_P();
            DeliverFromBuffer();
            return;
        }
        System.out.println("Process");
        _process.PrintV_P();
        System.out.println("Message");
        for (int i = 0; i < message.v_p.length; i++) {
            for (int j = 0; j < message.v_p.length; j++) {
                System.out.print(message.v_p[i][j] + " ");
            }
            System.out.println(" ");
        }
        _bufferQueue.add(message);
        LogHandler.Log("Buffer: " + message.message + " from " + message.fromId, _process.logFile);
    }

    private boolean CheckDelivery(Message message) {
        if (_process.v_p[message.fromId][message.toId] != message.v_p[message.fromId][message.toId] - 1)
            return false;
        for (int i = 0; i < message.v_p.length; i++) {
            if (i == message.fromId) {
                continue;
            }
            if (_process.v_p[i][message.toId] < message.v_p[i][message.toId]) {
                return false;
            }
        }
        return true;
    }

    private void DeliverFromBuffer() {
        boolean delivered = false;
        do {
            delivered = false;
            Iterator<Message> iterator = _bufferQueue.iterator();
            while (iterator.hasNext()) {
                Message msg = iterator.next();
                if (CheckDelivery(msg)) {
                    LogHandler.Log("Get from buffer: " + msg.message + " from " + msg.fromId, _process.logFile);
                    _process.MergeV_P(msg.v_p);
                    System.out.println("Process");
                    _process.PrintV_P();
                    iterator.remove();
                    delivered = true;
                }
            }
        } while (delivered);
    }
}
