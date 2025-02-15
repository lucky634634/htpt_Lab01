import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Program requires at least one argument");
            System.exit(1);
        }
        System.out.println(args[0]); 
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        sc.close();
    }
}
