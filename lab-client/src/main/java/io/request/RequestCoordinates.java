package io.request;

import data.Coordinates;
import io.InputData;
import io.Printer;
import util.RequestElement;

import java.util.Scanner;

import static io.ConsoleColor.OBJECT;

public class RequestCoordinates {
    private final RequestElement requestElement;
    private final Printer printer;
    private final InputData inputData;
    private final Scanner sc;

    public RequestCoordinates(RequestElement requestElement, Scanner sc, Printer printer, InputData inputData) {
        this.requestElement = requestElement;
        this.printer = printer;
        this.sc = sc;
        this.inputData = inputData;
    }

    public Coordinates.Builder get() {
        Coordinates.Builder coordinatesBuilder = new Coordinates.Builder();
        printer.println("Enter the data to create the Coordinate object: ", OBJECT);
        requestElement.get("    Enter the X coordinate: ", sc, printer,
                inputData::getIntCoordinate,
                coordinatesBuilder::withX, true);
        requestElement.get("    Enter the Y coordinate: ", sc, printer,
                inputData::getDoubleCoordinate,
                coordinatesBuilder::withY, true);
        return coordinatesBuilder;

    }
}
