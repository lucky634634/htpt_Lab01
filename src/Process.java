import java.util.concurrent.Semaphore;

public class Process {
    public ProcessInfo currentProcessInfo;

    public ProcessInfo[] processInfos;

    private int[][] _v_p = null;
    private int[] _timestamp = null;

    private SendHandler _sendHandler = null;
    private ReceiveHandler _receiveHandler = null;

    private Semaphore _semaphore = new Semaphore(1);

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
        _semaphore.acquireUninterruptibly();
        int[][] v_p = new int[_v_p.length][_v_p.length];
        for (int i = 0; i < _v_p.length; i++) {
            for (int j = 0; j < _v_p.length; j++) {
                v_p[i][j] = _v_p[i][j];
            }
        }
        _semaphore.release();
        return v_p;
    }

    public int[] GetTimestamp() {
        _semaphore.acquireUninterruptibly();
        int[] timestamp = new int[_timestamp.length];
        for (int i = 0; i < _timestamp.length; i++) {
            timestamp[i] = _timestamp[i];
        }
        _semaphore.release();
        return timestamp;
    }

    public void IncreaseTimestamp() {
        _semaphore.acquireUninterruptibly();
        _timestamp[currentProcessInfo.id] += 1;
        _semaphore.release();
    }

    public Message CreateMessageToSend(int fromId, String message, int toId) {
        _semaphore.acquireUninterruptibly();
        _timestamp[toId] += 1;
        Message msg = new Message(fromId, message, toId, _timestamp, _v_p);
        for (int i = 0; i < _timestamp.length; i++) {
            _v_p[fromId][i] = _timestamp[i];
        }
        _semaphore.release();
        return msg;
    }
}
