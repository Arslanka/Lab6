package data;

import java.io.Serializable;

public enum Country implements Serializable {

    UNITED_KINGDOM("UNITED_KINGDOM"),

    GERMANY("GERMANY"),

    THAILAND("THAILAND");

    Country(String name) {
    }

    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Country country : values()) {
            nameList.append(country.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}