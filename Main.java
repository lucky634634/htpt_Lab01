import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int numProcesses = 12;
        Process[] processes = new Process[numProcesses];

        // Tao cac process con
        for (int i = 0; i < numProcesses; i++) {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "ChildProcess.java", String.valueOf(i+1));
            processes[i] = processBuilder.start();
        }

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