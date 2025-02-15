import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Program requires at least one argument");
            System.exit(1);
        }
        System.out.println(args[0]);
        ReadConfig("config.txt");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter to continue");
        sc.nextLine();
        sc.close();
    }

    public static void ReadConfig(String path) {
        File file = new File(path);
        if (!file.exists())
            return;
        System.out.println(file.getName());
    }
}
