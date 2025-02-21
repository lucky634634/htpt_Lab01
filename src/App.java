import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Please provide 2 arguments");
            System.exit(1);
        }
        System.out.println("config: " + args[0]);
        System.out.println("id: " + args[1]);
        int id = 0;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Id must be an integer");
            System.exit(1);
        }
        ClearLog(id);
        ProcessInfo[] processInfos = ReadConfig(args[0]);

        for (int i = 0; i < processInfos.length; i++) {
            System.out.println(processInfos[i]);
        }
        if (id >= processInfos.length) {
            System.err.println("Id must be less than " + processInfos.length);
            System.exit(1);
        }

        Process process = new Process(processInfos, id);
        process.run();

        Pause();
    }

    public static void Pause() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter to continue");
        sc.nextLine();
        sc.close();
        System.exit(0);
    }

    public static ProcessInfo[] ReadConfig(String fileName) {
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

    public static void ClearLog(int id) {
        try {
            File file = new File("log_" + id + ".log");
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
