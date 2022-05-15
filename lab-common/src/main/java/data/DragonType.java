package data;

import java.io.Serializable;

public enum DragonType implements Serializable {
    WATER("WATER"),
    UNDERGROUND("UNDERGROUND"),
    AIR("AIR"),
    FIRE("FIRE");

    DragonType(String name) {

    }

    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (DragonType dragonType : values()) {
            nameList.append(dragonType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}