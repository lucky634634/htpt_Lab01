import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int numProcesses = 12;
        Process[] processes = new Process[numProcesses];

        // Tao cac tieu trinh con
        for (int i = 0; i < numProcesses; i++) {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "ChildProcess.java", String.valueOf(i+1));
            processBuilder.inheritIO();
            processes[i] = processBuilder.start();
        }

        // Wait for all processes to complete
        for (Process process : processes) {
            process.waitFor();
        }
        Pause();
    }

    public static void Pause() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Program done. Press Enter to exit");
        sc.nextLine();
        sc.close();
    }
}