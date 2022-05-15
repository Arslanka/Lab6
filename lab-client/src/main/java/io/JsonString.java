package io;

import com.google.gson.*;
import data.Dragon;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class JsonString {
    public <T> T read(String string, Class<T> cls) throws IOException, JsonParseException {
        T t;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class
                    , (JsonDeserializer<ZonedDateTime>)
                            (json, type, jsonPrimitive)
                                    -> ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
            t = gson.fromJson(string, cls);
        } catch (JsonParseException | DateTimeParseException e) {
            throw new JsonParseException("Json parsing error. The data is incorrect.\n" + e.getMessage());
        }
        return t;
    }

    public Collection<Dragon> readCollection(String string) throws IOException, JsonParseException {
        Dragon[] dragons;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class
                    , (JsonDeserializer<ZonedDateTime>)
                            (json, type, jsonPrimitive)
                                    -> ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();

            dragons = gson.fromJson(string, Dragon[].class);
        } catch (JsonParseException | DateTimeParseException e) {
            throw new JsonParseException("Json parsing error. The data is incorrect.\n" + e.getLocalizedMessage());
        }
        return new ArrayList<>(Arrays.asList(dragons));
    }

    public String get(Set<Dragon> dragonSet) throws IOException, JsonIOException {
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, (JsonSerializer<ZonedDateTime>)
                            (date, type, jsonSerializationContext)
                                    -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)))
                    .setPrettyPrinting().create();
            return (gson.toJson(dragonSet));
        } catch (JsonIOException e) {
            throw new JsonIOException("Error converting to Json. The data in the collection is incorrect\n" + e.getMessage());
        }
    }

    public <T> String get(Class<T> t) throws IOException, JsonIOException {
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, (JsonSerializer<ZonedDateTime>)
                            (date, type, jsonSerializationContext)
                                    -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)))
                    .setPrettyPrinting().create();
            return (gson.toJson(t));
        } catch (JsonIOException e) {
            throw new JsonIOException("Error converting to Json. The data in the collection is incorrect\n" + e.getMessage());
        }
    }
}
