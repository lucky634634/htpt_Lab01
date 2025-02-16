public class ProcessHandler extends Thread {
    public int port = 0;
    public ProcessInfo toProcessInfo;

    public ProcessHandler(int port, ProcessInfo toProcessInfo) {
        this.port = port;
        this.toProcessInfo = toProcessInfo;
    }

    public void run() {
    }
}