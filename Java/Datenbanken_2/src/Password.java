import java.io.Console;

/**
 * Created by leonard on 16.11.16.
 */
public class Password {


    public static String readPasswordFromConsole() {
        Console console = System.console();
        console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        //console.printf("Testing password%n");
        char passwordArray[] = console.readPassword("Enter your secret password: ");
        String pw = new String(passwordArray);
        //console.printf("Password entered was: %s%n", pw);
        return pw;
    }
}
