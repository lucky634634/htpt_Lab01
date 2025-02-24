import java.util.concurrent.Semaphore;

public class Process {
    public ProcessInfo currentProcessInfo;

    public ProcessInfo[] processInfos;

    private int[][] _v_p = null;
    private int[] _timestamp = null;

    private SendHandler _sendHandler = null;
    private ReceiveHandler _receiveHandler = null;
    public String logFile = "";

    public Semaphore semaphore = new Semaphore(1);

    public Process(ProcessInfo[] processInfos, int id) {
        this.processInfos = processInfos;
        this.currentProcessInfo = this.processInfos[id];
        _v_p = new int[processInfos.length][processInfos.length];
        for (int i = 0; i < processInfos.length; i++) {
            for (int j = 0; j < processInfos.length; j++) {
                _v_p[i][j] = 0;
            }
        }
        _timestamp = new int[processInfos.length];
        for (int i = 0; i < processInfos.length; i++) {
            _timestamp[i] = 0;
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

    public int[][] GetV_p() {
        int[][] v_p = new int[_v_p.length][_v_p.length];
        for (int i = 0; i < _v_p.length; i++) {
            for (int j = 0; j < _v_p.length; j++) {
                v_p[i][j] = _v_p[i][j];
            }
        }
        return v_p;
    }

    public int[] GetTimestamp() {
        int[] timestamp = new int[_timestamp.length];
        for (int i = 0; i < _timestamp.length; i++) {
            timestamp[i] = _timestamp[i];
        }
        return timestamp;
    }

    public void IncreaseTimestamp(int id) {
        _timestamp[id] += 1;
    }

    public Message CreateMessageToSend(String message, int toId) {
        semaphore.acquireUninterruptibly();
        IncreaseTimestamp(currentProcessInfo.id);
        Message msg = new Message(currentProcessInfo.id, message, toId, _timestamp, _v_p);
        for (int i = 0; i < _timestamp.length; i++) {
            _v_p[currentProcessInfo.id][i] = _timestamp[i];
        }
        PrintTimeStamp();
        semaphore.release();
        return msg;
    }

    public void UpdateTimestamp(int[] timestamp) {
        for (int i = 0; i < timestamp.length; i++) {
            _timestamp[i] = Math.max(_timestamp[i], timestamp[i]);
        }
    }

    public void UpdateV_p(int[][] v_p) {
    }

    public void PrintTimeStamp() {
        String ts = "<";
        for (int i = 0; i < _timestamp.length; i++) {
            ts += _timestamp[i];
            if (i != _timestamp.length - 1) {
                ts += ", ";
            }
        }
        ts += ">";
        System.out.println(ts);
    }
}
