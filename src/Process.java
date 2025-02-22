import java.util.concurrent.Semaphore;

public class Process {
    public ProcessInfo currentProcessInfo;

    public ProcessInfo[] processInfos;

    public int[][] v_p = null;
    public int[] timestamp = null;

    private SendHandler _sendHandler = null;
    private ReceiveHandler _receiveHandler = null;

    private Semaphore _semaphore = new Semaphore(1);

    public Process(ProcessInfo[] processInfos, int id) {
        this.processInfos = processInfos;
        this.currentProcessInfo = this.processInfos[id];
        v_p = new int[processInfos.length][processInfos.length];
        for (int i = 0; i < processInfos.length; i++) {
            for (int j = 0; j < processInfos.length; j++) {
                v_p[i][j] = 0;
            }
        }
        timestamp = new int[processInfos.length];
        for (int i = 0; i < processInfos.length; i++) {
            timestamp[i] = 0;
        }
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

    public void UpdateV(int fromId, int toId) {
        timestamp[fromId] += 1;
    }

    public void UpdateV_p(int[][] v_p) {
        for (int i = 0; i < this.v_p.length; i++) {
            for (int j = 0; j < this.v_p.length; j++) {
                if (i == currentProcessInfo.id) {
                    this.v_p[i][j] = 0;
                    continue;
                }
                this.v_p[i][j] = Math.max(v_p[i][j], this.v_p[i][j]);
            }
        }
    }
}
