import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public int fromId;
    public String message;
    public int toId;

    public int[] timeStamp = null;
    public int[][] v_p = null;

    public Message(int fromId, String message, int toId, int[] timeStamp, int[][] v_p) {
        this.fromId = fromId;
        this.message = message;
        this.toId = toId;
        this.timeStamp = new int[timeStamp.length];
        for (int i = 0; i < timeStamp.length; i++) {
            this.timeStamp[i] = timeStamp[i];
        }
        this.v_p = new int[v_p.length][v_p.length];
        for (int i = 0; i < v_p.length; i++) {
            for (int j = 0; j < v_p.length; j++) {
                this.v_p[i][j] = v_p[i][j];
            }
        }
    }

    @Override
    public String toString() {
        return "Message from " + fromId + ": " + message;
    }

    public boolean IsVpEmpty() {
        if (v_p == null) {
            return true;
        }
        for (int i = 0; i < timeStamp.length; i++) {
            for (int j = 0; j < timeStamp.length; j++) {
                if (v_p[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
