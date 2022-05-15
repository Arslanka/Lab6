package io.request;

import data.Country;
import data.Person;
import io.InputData;
import io.Printer;

import java.util.Scanner;

import static io.ConsoleColor.*;

public class RequestPerson {
    private final RequestElement requestElement;
    private final Printer printer;
    private final Scanner sc;
    private final InputData inputData;

    public RequestPerson(RequestElement requestElement, Scanner sc, Printer printer, InputData inputData) {
        this.requestElement = requestElement;
        this.printer = printer;
        this.sc = sc;
        this.inputData = inputData;
    }

    public Person.Builder get() {
        Person.Builder personBuilder = new Person.Builder();
        RequestLocation requestLocation = new RequestLocation(requestElement, sc, printer, inputData);
        printer.println("Enter the data to create the object " + "Person" + ":", OBJECT);
        requestElement.get("    Enter a name: ",
                sc, printer,
                inputData::getName,
                personBuilder::withName, true);
        requestElement.get("    Enter your birthday in the format yyyy-MM-dd HH:mm:ss a z: ",
                sc, printer,
                inputData::getDateTime,
                personBuilder::withBirthday, true);
        requestElement.get(HELP.wrapped("    Possible countries: " + Country.nameList()) + FIELD.wrapped("\n    Enter your nationality: "),
                sc, printer,
                inputData::getNationality,
                personBuilder::withNationality, true);
        personBuilder.withLocation(requestLocation.get().build());
        return personBuilder;
    }
}
