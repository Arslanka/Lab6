package io.request;

import data.Location;
import io.InputData;
import io.Printer;

import java.util.Scanner;

import static io.ConsoleColor.OBJECT;

public class RequestLocation {
    private final RequestElement requestElement;
    private final Printer printer;
    private final Scanner sc;
    private final InputData inputData;

    public RequestLocation(RequestElement requestElement, Scanner sc, Printer printer, InputData inputData) {
        this.requestElement = requestElement;
        this.printer = printer;
        this.sc = sc;
        this.inputData = inputData;
    }

    public Location.Builder get() {
        Location.Builder locationBuilder = new Location.Builder();
        printer.println("Enter the data to create the object Location: ", OBJECT);
        requestElement.get("    Enter the X coordinate: ",
                sc, printer,
                inputData::getIntCoordinate,
                locationBuilder::withX, true);
        requestElement.get("    Enter the Y coordinate: ",
                sc, printer,
                inputData::getIntCoordinate,
                locationBuilder::withY, true);
        requestElement.get("    Enter the Z coordinate: ",
                sc, printer,
                inputData::getIntCoordinate,
                locationBuilder::withZ, true);
        requestElement.get("    Enter a name: ",
                sc, printer,
                inputData::getName,
                locationBuilder::withName, true);
        return locationBuilder;
    }
}
