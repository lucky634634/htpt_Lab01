import java.util.concurrent.Semaphore;

public class Process {
    public ProcessInfo currentProcessInfo;

    public ProcessInfo[] processInfos;

    public int[][] v_p = null;

    private SendHandler _sendHandler = null;
    private ReceiveHandler _receiveHandler = null;
    public String logFile = "";

    public Semaphore semaphore = new Semaphore(1);

    public Process(ProcessInfo[] processInfos, int id) {
        this.processInfos = processInfos;
        this.currentProcessInfo = this.processInfos[id];
        v_p = new int[processInfos.length][processInfos.length];
        for (int i = 0; i < processInfos.length; i++) {
            for (int j = 0; j < processInfos.length; j++) {
                v_p[i][j] = 0;
            }
        }

        logFile = "log_" + id + ".log";
    }

    public void run() {
        try {

            _receiveHandler = new ReceiveHandler(this);
            _sendHandler = new SendHandler(this);
            _receiveHandler.start();
            _sendHandler.start();
            _sendHandler.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message CreateMessageToSend(String message, int toId) {
        semaphore.acquireUninterruptibly();
        v_p[currentProcessInfo.id][toId]++;
        Message msg = new Message(currentProcessInfo.id, message, toId, v_p);
        semaphore.release();
        return msg;
    }

    public void MergeV_P(int[][] v_p) {
        for (int i = 0; i < this.v_p.length; i++) {
            for (int j = 0; j < this.v_p.length; j++) {
                this.v_p[i][j] = Math.max(this.v_p[i][j], v_p[i][j]);
            }
        }
    }
}
