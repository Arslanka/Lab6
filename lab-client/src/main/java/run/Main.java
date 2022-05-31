package run;


import io.ClientApplication;
import io.Printer;


public class Main {
    public static void main(String[] args) {
        ClientApplication application = new ClientApplication(new Printer(false));
        application.startInteractiveMode();
    }
}