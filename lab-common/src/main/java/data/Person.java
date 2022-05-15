package data;

import exceptions.InvalidObjectFieldException;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Person implements Comparable<Person>, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private ZonedDateTime birthday; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    private Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidObjectFieldException {
        if (name == null) {
            throw new InvalidObjectFieldException("The name cannot be null");
        }
        if (name.isEmpty()) {
            throw new InvalidObjectFieldException("The name cannot be \"");
        }
        this.name = name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) throws InvalidObjectFieldException {
        if (birthday == null) {
            throw new InvalidObjectFieldException("Birthday cannot be null");
        }
        this.birthday = birthday;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) throws InvalidObjectFieldException {
        if (nationality == null) {
            throw new InvalidObjectFieldException("Nationality cannot be null");
        }
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(Person personObj) {
        return name.compareTo(personObj.getName());
    }

    public static class Builder {
        private final Person newPerson;

        public Builder() {
            newPerson = new Person();
        }

        public Builder withName(String name) throws InvalidObjectFieldException {
            newPerson.setName(name);
            return this;
        }

        public Builder withBirthday(ZonedDateTime birthday) throws InvalidObjectFieldException {
            newPerson.setBirthday(birthday);
            return this;
        }

        public Builder withNationality(Country nationality) throws InvalidObjectFieldException {
            newPerson.setNationality(nationality);
            return this;
        }

        public Builder withLocation(Location location) throws InvalidObjectFieldException {
            newPerson.setLocation(location);
            return this;
        }

        public Person build() throws InvalidObjectFieldException {
            return newPerson;
        }
    }

    public Person validated() throws InvalidObjectFieldException {
        setName(this.name);
        setBirthday(this.birthday);
        setNationality(this.nationality);
        setLocation(this.location.validated());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(birthday, person.birthday) && nationality == person.nationality && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, nationality, location);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }
}
