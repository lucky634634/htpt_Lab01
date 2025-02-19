import java.io.*;
import java.net.*;

public class SendHandler extends Thread {
    private Process _process = null;

    public SendHandler(Process process) {
        this._process = process;
    }

    public void run() {
        try {
            Thread.sleep(1000);
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
        Thread[] st = new Thread[10];
        for (int i = 0; i < 10; i++) {
            Message message = new Message(_process.currentProcessInfo.id, "Message " + i, processInfo.id, _process.v_p);
            st[i] = new Thread(() -> SendMessage(processInfo, message));
            st[i].start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                st[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void SendMessage(ProcessInfo processInfo, Message message) {
        try {
            Socket socket = new Socket(processInfo.addr, processInfo.port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
