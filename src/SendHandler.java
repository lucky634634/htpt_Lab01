import java.io.*;
import java.net.*;

public class SendHandler extends Thread {
    private Process _process = null;

    public SendHandler(Process process) {
        this._process = process;
    }

    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("SendHandler started");
            Thread[] threads = new Thread[_process.processInfos.length - 1];
            for (int i = 0, j = 0; i < _process.processInfos.length; i++) {
                if (_process.processInfos[i].id != _process.currentProcessInfo.id) {
                    ProcessInfo processInfo = _process.processInfos[i];
                    threads[j] = new Thread(() -> HandleSendMessage(processInfo));
                    threads[j].start();
                    j++;
                }
            }

            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
            System.out.println("SendHandler finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandleSendMessage(ProcessInfo processInfo) {
        try {
            final int numMessages = 150;
            Thread[] threads = new Thread[numMessages];
            for (int i = 0; i < numMessages; i++) {
                String msg = "Message " + i;
                Message message = _process.CreateMessageToSend(msg, processInfo.id);

                threads[i] = new Thread(
                        () -> SendMessage(processInfo, message));
                threads[i].start();
                Thread.sleep((long) (Math.random() * 1000));
            }
            for (int i = 0; i < numMessages; i++) {
                threads[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SendMessage(ProcessInfo processInfo, Message message) {
        while (true) {
            try {
                Socket socket = new Socket(processInfo.addr, processInfo.port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message);
                oos.flush();
                oos.close();
                socket.close();
                LogHandler.Log(message.message + " to " + processInfo.id, _process.logFile);
                return;
            } catch (Exception e) {
                LogHandler.Log(message.message + " to " + processInfo.id + " failed", _process.logFile);
            }
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
