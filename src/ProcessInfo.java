public class ProcessInfo {
    public int id = 0;
    public String addr = "127.0.0.1";
    public int port = 0;

    public ProcessInfo(int id, String addr, int port) {
        this.id = id;
        this.addr = addr;
        this.port = port;
    }

    @Override
    public String toString() {
        return "ProcessInfo {" +
                "id=" + id +
                ", addr='" + addr + '\'' +
                ", port=" + port +
                '}';
    }
}
