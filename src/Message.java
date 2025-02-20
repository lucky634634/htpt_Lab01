import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public int fromId;
    public String message;
    public int toId;

    public int[][] v_p = null;

    public Message(int fromId, String message, int toId, int[][] v_p) {
        this.fromId = fromId;
        this.message = message;
        this.toId = toId;
        this.v_p = v_p;
    }

    @Override
    public String toString() {
        return "Message from " + fromId + ": " + message;
    }

}
