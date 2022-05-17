package util;

import exceptions.InvalidObjectFieldException;
import io.Printer;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.ConsoleColor.ERROR;
import static io.ConsoleColor.FIELD;

public class RequestElement {
    public <T> void get(String prefix, Scanner sc, Printer printer, Function<String, T> cast, Consumer<T> action, boolean toLower) throws InvalidObjectFieldException {
        while (true) {
            try {
                printer.print(prefix, FIELD);
                if (toLower)
                    action.accept(cast.apply(sc.nextLine().trim().toLowerCase()));
                else
                    action.accept(cast.apply(sc.nextLine().trim()));
                return;
            } catch (InvalidObjectFieldException e) {
                printer.println(e.getMessage(), ERROR);
            }
        }
    }

    public <T> T get(String prefix, Scanner sc, Printer printer, Function<String, T> action, boolean toLower) {
        while (true) {
            try {
                printer.print(prefix, FIELD);
                if (toLower)
                    return action.apply(sc.nextLine().trim().toLowerCase());
                else return action.apply(sc.nextLine().trim());

            } catch (InvalidObjectFieldException e) {
                printer.println(e.getMessage(), ERROR);
            }
        }
    }

    public <T> boolean get(String prefix, Scanner sc, Printer printer, Function<String, T> action, Predicate<T> predicate, boolean toLower) {
        while (true) {
            try {
                printer.print(prefix, FIELD);
                if (toLower)
                    return predicate.test(action.apply(sc.nextLine().trim().toLowerCase()));
                else return predicate.test(action.apply(sc.nextLine().trim()));

            } catch (InvalidObjectFieldException e) {
                printer.println(e.getMessage(), ERROR);
            }
        }
    }
}
