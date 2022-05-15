package collection;

import data.Dragon;
import data.Person;
import exceptions.ExecutionException;
import exceptions.ExistingIdException;
import exceptions.IncorrectIdException;
import exceptions.InvalidObjectFieldException;
import file.JsonFile;
import io.ConsoleColor;
import io.Printer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeMap;

public class Collection implements Serializable {
    private final HashSet<Dragon> dragonHashSet = new HashSet<>();
    private final Printer printer = new Printer(false);
    public static final TreeMap<Integer, Dragon> idMap = new TreeMap<>();
    private final LocalDateTime creationDate = LocalDateTime.now();

    public Collection() {
    }

    public void add(Dragon dragon) throws ExistingIdException, ExecutionException {
        Integer dragonId = dragon.getId();
        try {
            if (dragon.validated() != null)
                if (idMap.containsKey(dragonId))
                    if (idMap.containsKey(dragonId))
                        throw new ExistingIdException(String
                                .format("%s %d %s", "There is already an object with id ", dragonId, "in the collection"));
            idMap.put(dragonId, dragon);
            dragonHashSet.add(dragon);
        } catch (InvalidObjectFieldException e) {
            throw new ExecutionException("You have entered incorrect fields for the dragon object\n" + e.getMessage(), e);
        }
    }

    public void add(java.util.Collection<Dragon> dragonList) {
        int dragonCount = 0;
        for (Dragon d : dragonList) {
            try {
                add(d);
                dragonCount++;
            } catch (ExecutionException e) {
                throw new ExecutionException("The file element with the number "
                        + ((dragonCount + 1) + " is not valid\n") + e.getMessage(), e);
            }
        }
    }

    public void show() {
        printer.printCollection(dragonHashSet, ConsoleColor.OBJECT);
    }

    public void updateId(Integer id, Dragon dragon) {
        dragonHashSet.remove(idMap.get(id));
        dragon.setId(id);
        try {
            if (dragon.validated() != null) {
                dragonHashSet.add(dragon);
                idMap.put(id, dragon);
            }
        } catch (InvalidObjectFieldException e) {
            throw new ExecutionException("You have entered incorrect fields for the dragon object", e);
        }
    }

    public void removeById(Integer id) {
        if (validatedId(id) > 0) {
            dragonHashSet.remove(idMap.get(id));
            idMap.remove(id);
        }
    }

    public void clear() {
        dragonHashSet.clear();
    }

    public void save(JsonFile jsonFile) {
        jsonFile.write(dragonHashSet);
    }

    public void removeLower(Dragon dragon) {
        dragonHashSet.removeIf(d -> d.compareTo(dragon) < 0);
        idMap
                .keySet()
                .removeIf(id -> idMap.get(id).compareTo(dragon) < 0);
    }

    public void removeGreater(Dragon dragon) {
        dragonHashSet.removeIf(d -> d.compareTo(dragon) > 0);
        idMap
                .keySet()
                .removeIf(id -> idMap.get(id).compareTo(dragon) > 0);
    }

    public void removeByWeight(Float weight) {
        dragonHashSet.removeIf(d -> (Objects.equals(d.getWeight(), weight)));
        idMap
                .keySet()
                .removeIf(id -> Objects.equals(idMap.get(id).getWeight(), weight));
    }


    public void info() {
        String collectionName = "HashSet<Dragon> dragonHashSet";
        printer.println(String.format("%-40s", "Collection Type: ") + " " + collectionName, ConsoleColor.HELP);
        printer.println(String.format("%-40s", "Date and time of creation: ") + " " + creationDate, ConsoleColor.HELP);
        printer.println(String.format("%-40s", "Collection size: ") + " " + dragonHashSet.size(), ConsoleColor.HELP);
    }

    public void filterGreaterThanAge(Long age) {
        printer.println("Collection items with an age field greater than the specified one: ", ConsoleColor.HELP);
        dragonHashSet
                .stream()
                .filter(d -> age.compareTo(d.getAge()) < 0)
                .forEach(d -> printer.println(d, ConsoleColor.OBJECT));
    }

    public void countGreaterThanKiller(Object personObj) {
        Person killer = (Person) personObj;
        long cnt = dragonHashSet
                .stream()
                .filter(d -> d.getKiller().compareTo(killer) > 0)
                .count();
        printer.println("Collection elements that have a killer field greater than the specified one – " + cnt, ConsoleColor.HELP);
    }

    public void addIfMax(Dragon dragon) {
        if (dragon.compareTo(Collections.max(dragonHashSet)) > 0) {
            dragonHashSet.add(dragon);
        } else {
            throw new ExecutionException("The entered element is less than the maximum from the collection");
        }
    }

    public static Integer mex() {
        if (idMap.isEmpty()) return 1;
        for (int i = 1; i <= idMap.lastKey() + 1; ++i) {
            if (!idMap.containsKey(i))
                return i;
        }
        return idMap.lastKey() + 1;
    }

    public Integer validatedId(Integer id) {
        if (!idMap.containsKey(id))
            throw new IncorrectIdException(String.format("%s %d %s", "The id with the number", id, "is not in the collection."));
        return id;
    }
}

