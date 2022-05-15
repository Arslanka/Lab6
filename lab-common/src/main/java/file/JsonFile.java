package file;

import com.google.gson.JsonIOException;
import data.Dragon;
import exceptions.JsonReadingException;
import exceptions.JsonWritingException;
import io.JsonString;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class JsonFile {
    private final TextFile textFile;

    public JsonFile(TextFile textFile) {
        this.textFile = textFile;
    }

    public Collection<Dragon> read() {
        try {
            return new JsonString().readCollection(textFile.read());
        } catch (IOException | JsonIOException e) {
            throw new JsonReadingException("The Json file cannot be read.\n " + e.getMessage());
        }
    }

    public void write(Set<Dragon> dragonSet) {
        try {
            textFile.write(new JsonString().get(dragonSet));
        } catch (IOException | JsonIOException e) {
            throw new JsonWritingException("Еhe Json file cannot be written\n" + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "textFile = " + textFile;
    }
}
