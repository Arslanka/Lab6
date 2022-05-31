import collection.Collection;
import io.Printer;
import util.ServerApplication;


public class ServerMain {
    public static void main(String[] args) {
        ServerApplication application =
                new ServerApplication(args,
                        new Collection(), new Printer(false));
        application.run();
    }
}
