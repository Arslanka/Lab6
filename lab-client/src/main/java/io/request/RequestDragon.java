package io.request;

import data.Color;
import data.Dragon;
import data.DragonType;
import exceptions.InvalidObjectFieldException;
import io.InputData;
import io.Printer;
import util.RequestElement;

import java.util.Scanner;

import static io.ConsoleColor.*;

public class RequestDragon {
    private final RequestElement requestElement;
    private final Printer printer;
    private final InputData inputData;
    private final Scanner sc;

    public RequestDragon(RequestElement requestElement, Scanner sc, Printer printer, InputData inputData) {
        this.requestElement = requestElement;
        this.sc = sc;
        this.printer = printer;
        this.inputData = inputData;
    }

    public Dragon.Builder get() throws InvalidObjectFieldException {
        Dragon.Builder dragonBuilder = new Dragon.Builder();
        RequestPerson requestPerson = new RequestPerson(requestElement, sc, printer, inputData);
        RequestCoordinates requestCoordinates = new RequestCoordinates(requestElement, sc, printer, inputData);
        printer.println("Enter the data to create the Dragon object: ", OBJECT);
        requestElement.get("    Enter a name: ", sc, printer,
                inputData::getName,
                dragonBuilder::withName, true);
        dragonBuilder.withCoordinates(requestCoordinates.get().build());
        requestElement.get("    Enter the age: ", sc, printer,
                inputData::getAge,
                dragonBuilder::withAge, true);
        requestElement.get("    Enter the weight: ", sc, printer,
                inputData::getWeight,
                dragonBuilder::withWeight, true);
        requestElement.get(HELP.wrapped("    Possible colors: " + Color.nameList()) + FIELD.wrapped("\n    Enter a color: "),
                sc, printer, inputData::getColor,
                dragonBuilder::withColor, true);
        requestElement.get(HELP.wrapped("    Possible types: " + DragonType.nameList()) + FIELD.wrapped("\n    Enter a type: "),
                sc, printer, inputData::getType,
                dragonBuilder::withType, true);
        dragonBuilder.withPerson(requestPerson.get().build());
        return dragonBuilder.withCreationDate();
    }
}
