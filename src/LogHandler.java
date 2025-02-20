import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalTime;

public class LogHandler {
    public static void Log(String message, int id) {
        LocalTime time = LocalTime.now();
        System.out.println("[" + time + "] " + message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log_" + id + ".log", true))) {
            writer.write("[" + time + "] " + message + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}