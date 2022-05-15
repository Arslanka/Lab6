package io;

public enum ConsoleColor {
    BLACK("\u001B[30m"),
    ERROR("\u001B[31m"),
    REQUEST("\u001B[32m"),
    CONSOLE("\u001B[33m"),
    OBJECT("\u001B[34m"),
    FIELD("\u001B[35m"),
    HELP("\u001B[36m"),
    WHITE("\u001B[37m");

    private static final String RESET = "\u001B[0m";
    private final String color;

    ConsoleColor(String color) {
        this.color = color;
    }

    public String wrapped(String text) {
        return color + text + RESET;
    }

}
