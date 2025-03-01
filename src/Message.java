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

    public void PrintV_P() {
        String result = "";
        for (int i = 0; i < v_p.length; i++) {
            for (int j = 0; j < v_p.length; j++) {
                result += v_p[i][j] + " ";
            }
            result += "\n";
        }
        System.out.println(result);
    }

}
