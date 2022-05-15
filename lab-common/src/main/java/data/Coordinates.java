package data;

import exceptions.InvalidObjectFieldException;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private Integer x; //Поле не может быть null
    private Double y; //Максимальное значение поля: 508, Поле не может быть null

    private Coordinates() {
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) throws InvalidObjectFieldException {
        if (x == null) {
            throw new InvalidObjectFieldException("The X coordinate cannot be null");
        }
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) throws InvalidObjectFieldException {
        if (y == null) {
            throw new InvalidObjectFieldException("The Y coordinate cannot be null");
        }
        if (y > 508)
            throw new InvalidObjectFieldException("The Y coordinate cannot be greater than 508");
        this.y = y;
    }


    public static class Builder {
        private final Coordinates newCoordinates;

        public Builder() {
            newCoordinates = new Coordinates();
        }

        public Builder withX(Integer x) throws InvalidObjectFieldException {
            newCoordinates.setX(x);
            return this;
        }

        public Builder withY(Double y) throws InvalidObjectFieldException {
            newCoordinates.setY(y);
            return this;
        }

        public Coordinates build() {
            return newCoordinates;
        }
    }

    public Coordinates validated() throws InvalidObjectFieldException {
        setX(this.x);
        setY(this.y);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String
    toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
