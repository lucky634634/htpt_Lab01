import java.time.LocalDateTime;

public class LogHandler {
    public static void Log(String message) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("[" + dateTime + "] " + message);
    }

}