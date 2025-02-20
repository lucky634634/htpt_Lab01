import java.time.LocalTime;

public class LogHandler {
    public static void Log(String message) {
        LocalTime time = LocalTime.now();
        System.out.println("[" + time + "] " + message);
    }

}