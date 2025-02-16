import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public ProcessInfo[] processInfos;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Program requires at least one argument");
            System.exit(1);
        }
        System.out.println(args[0]);
        App app = new App();

        app.processInfos = app.ReadConfig("config.cfg");

        for (int i = 0; i < app.processInfos.length; i++) {
            System.out.println(app.processInfos[i]);
        }

        Pause();
    }

    public static void Pause() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter to continue");
        sc.nextLine();
        sc.close();
    }

    public ProcessInfo[] ReadConfig(String fileName) {
        ArrayList<ProcessInfo> processes = new ArrayList<ProcessInfo>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int id = 0;
            String addr = "";
            int port = 0;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("[process]")) {
                    continue;
                }
                if (line.startsWith("id = ")) {
                    id = Integer.parseInt(line.split(" = ")[1].trim());
                    continue;
                }
                if (line.startsWith("addr = ")) {
                    addr = line.split(" = ")[1].trim();
                    continue;
                }
                if (line.startsWith("port = ")) {
                    port = Integer.parseInt(line.split(" = ")[1].trim());
                    processes.add(new ProcessInfo(id, addr, port));
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessInfo[] processInfos = new ProcessInfo[processes.size()];
        for (int i = 0; i < processes.size(); i++) {
            processInfos[i] = processes.get(i);
        }
        return processInfos;
    }
}
