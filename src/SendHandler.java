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
            for (int i = 0; i < 150; i++) {
                _process.IncreaseTimestamp();
                SendMessage(processInfo,
                        _process.CreateMessageToSend(_process.currentProcessInfo.id, "Message " + i, processInfo.id));
                Thread.sleep((long) (Math.random() * 1000));
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
                LogHandler.Log(message.message + " to " + processInfo.id, _process.currentProcessInfo.id);
                return;
            } catch (Exception e) {
                LogHandler.Log(message.message + " to " + processInfo.id + " failed", _process.currentProcessInfo.id);
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
