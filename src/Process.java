public class Process {
    public ProcessInfo currentProcessInfo;

    public ProcessInfo[] processInfos;

    public int[][] v_p = null;

    private SendHandler _sendHandler = null;
    private ReceiveHandler _receiveHandler = null;

    public Process(ProcessInfo[] processInfos, int id) {
        this.processInfos = processInfos;
        this.currentProcessInfo = this.processInfos[id];
        v_p = new int[processInfos.length][processInfos.length];
        for (int i = 0; i < processInfos.length; i++) {
            for (int j = 0; j < processInfos.length; j++) {
                v_p[i][j] = 0;
            }
        }
    }

    public void run() {
        _receiveHandler = new ReceiveHandler(this);
        _sendHandler = new SendHandler(this);
        _receiveHandler.start();
        _sendHandler.start();
    }
}
