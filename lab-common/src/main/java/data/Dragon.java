package data;

import collection.Collection;
import exceptions.InvalidObjectFieldException;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


public class Dragon implements Comparable<Dragon>, Serializable {

    private Integer id = Collection.mex();
    ;//Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0
    private Float weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonType type; //Поле может быть null
    private Person killer; //Поле может быть null

    private Dragon() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidObjectFieldException {
        if (name == null) {
            throw new InvalidObjectFieldException("The name cannot be null");
        }
        if (name.isEmpty()) {
            throw new InvalidObjectFieldException("The name cannot be \"\"");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) throws InvalidObjectFieldException {
        if (coordinates == null) throw new InvalidObjectFieldException("The coordinates cannot be null");
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        creationDate = ZonedDateTime.now();
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) throws InvalidObjectFieldException {
        if (age == null) throw new InvalidObjectFieldException("The age cannot be null");
        if (age <= 0) throw new InvalidObjectFieldException("The age cannot be less than or equal to 0");
        this.age = age;
    }

    public void setId(Integer id) throws InvalidObjectFieldException {
        if (id == null) throw new InvalidObjectFieldException("The id cannot be null");
        if (id <= 0) throw new InvalidObjectFieldException("The id value must be positive");
        if (id >= Integer.MAX_VALUE)
            throw new InvalidObjectFieldException("The id value must be less than " + Integer.MAX_VALUE);
        this.id = id;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) throws InvalidObjectFieldException {
        if (weight == null) throw new InvalidObjectFieldException("The weight cannot be null");
        if (weight <= 0) throw new InvalidObjectFieldException("The weight cannot be less than or equal to 0");
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) throws InvalidObjectFieldException {
        if (color == null) throw new InvalidObjectFieldException("The color cannot be null");
        this.color = color;
    }

    public DragonType getType() {
        return type;
    }

    public void setType(DragonType type) throws InvalidObjectFieldException {
        if (type == null) throw new InvalidObjectFieldException("The type cannot be null");
        this.type = type;
    }

    public Person getKiller() {
        return killer;
    }

    public void setKiller(Person killer) throws InvalidObjectFieldException {
        if (killer == null) throw new InvalidObjectFieldException("The killer cannot be null");
        this.killer = killer;
    }

    @Override
    public int compareTo(Dragon dragonObj) {
        return age.compareTo(dragonObj.getAge());
    }

    public static class Builder {
        private final Dragon newDragon;

        public Builder() {
            newDragon = new Dragon();
        }


        public Builder withName(String name) throws InvalidObjectFieldException {
            newDragon.setName(name);
            return this;
        }

        public Builder withCoordinates(Coordinates coordinates) throws InvalidObjectFieldException {
            newDragon.setCoordinates(coordinates);
            return this;
        }

        public Builder withCreationDate() {
            newDragon.setCreationDate();
            return this;
        }

        public Builder withAge(Long age) throws InvalidObjectFieldException {
            newDragon.setAge(age);
            return this;
        }

        public Builder withWeight(Float weight) throws InvalidObjectFieldException {
            newDragon.setWeight(weight);
            return this;
        }

        public Builder withColor(Color color) throws InvalidObjectFieldException {
            newDragon.setColor(color);
            return this;
        }

        public Builder withType(DragonType dragonType) throws InvalidObjectFieldException {
            newDragon.setType(dragonType);
            return this;
        }

        public Builder withPerson(Person killer) throws InvalidObjectFieldException {
            newDragon.setKiller(killer);
            return this;
        }


        public Dragon build() {
            return newDragon;
        }

    }

    public Dragon validated() throws InvalidObjectFieldException {
        setName(this.name);
        setId(this.id);
        setCreationDate();
        setCoordinates(this.coordinates.validated());
        setAge(this.age);
        setWeight(this.weight);
        setColor(this.color);
        setType(this.type);
        setKiller(this.killer.validated());
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return Objects.equals(id, dragon.id) && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && Objects.equals(creationDate, dragon.creationDate) && Objects.equals(age, dragon.age) && Objects.equals(weight, dragon.weight) && color == dragon.color && type == dragon.type && Objects.equals(killer, dragon.killer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, weight, color, type, killer);
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", age=" + age +
                ", weight=" + weight +
                ", color=" + color +
                ", type=" + type +
                ", killer=" + killer +
                '}';
    }
}
