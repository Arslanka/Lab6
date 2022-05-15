package data;

import exceptions.InvalidObjectFieldException;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {
    private Integer x; //Поле не может быть null
    private Integer y;
    private Integer z; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 346, Поле не может быть null

    private Location() {
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public void setX(Integer x) throws InvalidObjectFieldException {
        if (x == null) {
            throw new InvalidObjectFieldException("The X coordinate cannot be null");
        }
        this.x = x;
    }

    public void setY(Integer y) throws InvalidObjectFieldException {
        if (y == null) {
            throw new InvalidObjectFieldException("The Y coordinate cannot be null");
        }
        this.y = y;
    }

    public void setZ(Integer z) throws InvalidObjectFieldException {
        if (z == null) {
            throw new InvalidObjectFieldException("The Z coordinate cannot be null");
        }
        this.z = z;
    }

    public void setName(String name) throws InvalidObjectFieldException {
        if (name == null) {
            throw new InvalidObjectFieldException("The name cannot be null");
        }

        if (name.length() > 346) {
            throw new InvalidObjectFieldException("The length of the name cannot be more than 346");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private final Location newLocation;

        public Builder() {
            newLocation = new Location();
        }

        public Builder withName(String name) throws InvalidObjectFieldException {
            newLocation.setName(name);
            return this;
        }

        public Builder withX(Integer x) throws InvalidObjectFieldException {
            newLocation.setX(x);
            return this;
        }

        public Builder withY(Integer y) throws InvalidObjectFieldException {
            newLocation.setY(y);
            return this;
        }

        public Builder withZ(Integer z) throws InvalidObjectFieldException {
            newLocation.setZ(z);
            return this;
        }

        public Location build() {
            return newLocation;
        }
    }

    public Location validated() throws InvalidObjectFieldException {
        setX(this.x);
        setY(this.y);
        setZ(this.z);
        setName(this.name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(y, location.y) && Objects.equals(x, location.x) && Objects.equals(z, location.z) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}
