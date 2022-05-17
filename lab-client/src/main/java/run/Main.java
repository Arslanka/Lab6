package run;


import io.ClientApplication;
import io.Printer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClientApplication application = new ClientApplication( new Printer(false));
        application.startInteractiveMode();
    }
}