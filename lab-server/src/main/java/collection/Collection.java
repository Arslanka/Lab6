package collection;

import data.Dragon;
import data.Person;
import exceptions.ExecutionException;
import exceptions.ExistingIdException;
import exceptions.IncorrectIdException;
import exceptions.InvalidObjectFieldException;
import file.JsonFile;
import io.Printer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeMap;

import static io.ConsoleColor.HELP;
import static io.ConsoleColor.OBJECT;

public class Collection implements Serializable {
    private final HashSet<Dragon> dragonHashSet = new HashSet<>();
    private final Printer printer = new Printer(false);
    public static final TreeMap<Integer, Dragon> idMap = new TreeMap<>();
    private final LocalDateTime creationDate = LocalDateTime.now();

    public Collection() {
    }

    public String add(Dragon dragon) throws ExistingIdException, ExecutionException {
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
        return HELP.wrapped("The item was successfully added to the collection\n");
    }

    public String add(java.util.Collection<Dragon> dragonList) {
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
        return HELP.wrapped("The item(s) was successfully added to the collection\n");
    }

    public String show() {
        StringBuilder builder = new StringBuilder();
        dragonHashSet.forEach(d -> builder.append(d).append('\n'));
        return OBJECT.wrapped(builder.toString());
    }

    public String updateId(Integer id, Dragon dragon) {
        try {
            validatedId(id);
            dragonHashSet.remove(idMap.get(id));
            dragon.setId(id);
            if (dragon.validated() != null) {
                dragonHashSet.add(dragon);
                idMap.put(id, dragon);
            }
        } catch (InvalidObjectFieldException e) {
            throw new ExecutionException("You have entered incorrect fields for the dragon object", e);
        } catch (IncorrectIdException e) {
            throw new ExecutionException(e.getMessage());
        }
        return HELP.wrapped("Collection item with id " + id + " successfully updated\n");
    }

    public String removeById(Integer id) {
        if (validatedId(id) > 0) {
            dragonHashSet.remove(idMap.get(id));
            idMap.remove(id);
        }
        return HELP.wrapped("An element whose id field value is equivalent to the specified " + id + " has been successfully removed from the collection\n");
    }

    public String clear() {
        dragonHashSet.clear();
        return HELP.wrapped("The collection has been cleared\n");

    }

    public String save(JsonFile jsonFile) {
        jsonFile.write(dragonHashSet);
        return HELP.wrapped("The collection is saved to  " + jsonFile + '\n');
    }

    public String removeLower(Dragon dragon) {
        dragonHashSet.removeIf(d -> d.compareTo(dragon) < 0);
        idMap
                .keySet()
                .removeIf(id -> idMap.get(id).compareTo(dragon) < 0);
        return HELP.wrapped("All items in the collection smaller than this one have been deleted\n");
    }

    public String removeGreater(Dragon dragon) {
        dragonHashSet.removeIf(d -> d.compareTo(dragon) > 0);
        idMap
                .keySet()
                .removeIf(id -> idMap.get(id).compareTo(dragon) > 0);
        return HELP.wrapped("All items in the collection greater than this one have been deleted\n");

    }

    public String removeByWeight(Float weight) {
        dragonHashSet.removeIf(d -> (Objects.equals(d.getWeight(), weight)));
        idMap
                .keySet()
                .removeIf(id -> Objects.equals(idMap.get(id).getWeight(), weight));
        return HELP.wrapped(String.format("%s %f %s", "Elements whose weight field value is equivalent to", weight, "the specified one have been successfully removed from the collection"));
    }


    public String info() {
        String collectionName = "HashSet<Dragon> dragonHashSet";
        return HELP.wrapped(String.format("%-40s", "Collection Type: ") + " " + collectionName + '\n'
                + String.format("%-40s", "Date and time of creation: ") + " " + creationDate + '\n'
                + String.format("%-40s", "Collection size: ") + " " + dragonHashSet.size() + '\n');
    }

    public String filterGreaterThanAge(Long age) {
        StringBuilder builder = new StringBuilder();
        builder.append(HELP.wrapped("Collection items with an age field greater than the specified one: "));
        dragonHashSet
                .stream()
                .filter(d -> age.compareTo(d.getAge()) < 0)
                .forEach(d -> builder.append(OBJECT.wrapped(d.toString())));
        return builder.toString();
    }

    public String countGreaterThanKiller(Object personObj) {
        Person killer = (Person) personObj;
        long cnt = dragonHashSet
                .stream()
                .filter(d -> d.getKiller().compareTo(killer) > 0)
                .count();
        return HELP.wrapped("Collection elements that have a killer field greater than the specified one – " + cnt + '\n');
    }

    public String addIfMax(Dragon dragon) {
        if (dragon.compareTo(Collections.max(dragonHashSet)) > 0) {
            dragonHashSet.add(dragon);
        } else {
            throw new ExecutionException("The entered element is less than the maximum from the collection");
        }
        return HELP.wrapped("The item was successfully added to the collection");
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

