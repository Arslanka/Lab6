package io;

import data.Color;
import data.Country;
import data.DragonType;
import exceptions.FieldTypeMismatchException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;


public class InputData {
    public InputData() {
    }


    public String getName(String name) {
        try {
            return (name.equals("") ? null : name);
        } catch (InputMismatchException e) {
            throw new FieldTypeMismatchException("You entered an incorrect name. Please enter the name again");
        }
    }

    public String getFileName(String fileName) {
        try {
            return (fileName.equals("") ? null : fileName);
        } catch (InputMismatchException e) {
            throw new FieldTypeMismatchException("You have entered an incorrect file name. Please enter the name again");
        }
    }

    public ZonedDateTime getDateTime(String dateTime) {
        try {
            ZoneId timeZone = ZoneId.systemDefault();
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME).atZone(timeZone);
        } catch (DateTimeException e) {
            throw new FieldTypeMismatchException("You entered an incorrect date. Please enter the date again");
        }
    }

    public Integer getIntCoordinate(String intCoordinate) {
        try {
            return Integer.parseInt(intCoordinate);
        } catch (InputMismatchException | NumberFormatException | IllegalStateException e) {
            throw new FieldTypeMismatchException("You entered an incorrect coordinate. Please enter the coordinate again");
        }
    }

    public Double getDoubleCoordinate(String doubleCoordinate) {
        try {
            return Double.parseDouble(doubleCoordinate);
        } catch (InputMismatchException | NumberFormatException | IllegalStateException e) {
            throw new FieldTypeMismatchException("You have entered an incorrect floating point coordinate. Please enter the coordinate again");
        }
    }


    public Country getNationality(String nationality) {
        try {
            return Country.valueOf(nationality.toUpperCase());
        } catch (InputMismatchException | IllegalArgumentException e) {
            throw new FieldTypeMismatchException("You have entered an incorrect nationality. Please enter your nationality again");
        }
    }

    public Color getColor(String color) {
        try {
            return Color.valueOf(color.toUpperCase());
        } catch (InputMismatchException | IllegalArgumentException e) {
            throw new FieldTypeMismatchException("You have entered an incorrect color. Please enter the color again");
        }
    }

    public DragonType getType(String type) {
        try {
            return DragonType.valueOf(type.toUpperCase());
        } catch (InputMismatchException | IllegalArgumentException e) {
            throw new FieldTypeMismatchException("You have entered an incorrect type. Please enter the type again");
        }
    }

    public Long getAge(String age) {
        try {
            return Long.parseLong(age);
        } catch (InputMismatchException | NumberFormatException e) {
            throw new FieldTypeMismatchException("You entered an incorrect age. Please enter the age again");
        }
    }

    public Float getWeight(String weight) {
        try {
            return Float.parseFloat(weight);
        } catch (InputMismatchException | NumberFormatException e) {
            throw new FieldTypeMismatchException("You entered an incorrect weight. Please enter the weight again");
        }
    }

    public Integer getId(String id) {
        try {
            return (id.equals("") ? null : Integer.parseInt(id));
        } catch (InputMismatchException | NumberFormatException e) {
            throw new FieldTypeMismatchException("You entered an incorrect id. Please enter the id again");
        }
    }
}
