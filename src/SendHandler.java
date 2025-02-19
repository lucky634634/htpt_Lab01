import java.io.*;
import java.net.*;

public class SendHandler extends Thread {
    private Process _process = null;

    public SendHandler(Process process) {
        this._process = process;
    }

    public void run() {
    }

}
