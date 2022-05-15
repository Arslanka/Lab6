package file;

import exceptions.FileReadPermissionException;
import exceptions.InputOutputException;
import io.Printer;

import java.io.File;
import java.io.FileNotFoundException;

public class FileManager {
    private final String fileName;
    private final Printer printer;

    public FileManager(String fileName, Printer printer) {
        this.fileName = fileName;
        this.printer = printer;
    }

    public TextFile getTextFileByName() {
        try {
            return new TextFile(new File(this.fileName));
        } catch (FileNotFoundException | FileReadPermissionException e) {
            throw new InputOutputException(e.getMessage());
        } catch (NullPointerException e) {
            throw new InputOutputException("You didn't enter the file name");
        }
    }

    public JsonFile getJsonFileByName() {
        return new JsonFile(this.getTextFileByName());
    }

}
