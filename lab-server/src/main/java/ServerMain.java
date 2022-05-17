import collection.Collection;
import io.Printer;
import util.ServerApplication;

import java.util.Scanner;

public class ServerMain {

    public static void main(String[] args) {
        ServerApplication application =
                new ServerApplication(new Scanner(System.in).nextLine().split("\\s+"),
                        new Collection(), new Printer(false));
        application.run();
    }
}
