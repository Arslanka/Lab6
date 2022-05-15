package file;

import exceptions.FIleReadingException;
import exceptions.FIleWritingException;
import exceptions.FileLengthLimitException;
import exceptions.FileReadPermissionException;
import io.Readable;
import io.Writeable;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextFile implements Readable, Writeable {
    private final File file;
    private static final int MAX_FILE_LENGTH = 10000;

    public TextFile(File file) throws FileNotFoundException, FileReadPermissionException {
        this.file = validated(file);
    }

    @Override
    public String read() {
        try {
            final StringBuilder builder = new StringBuilder();
            final Reader in = new FileReader(file);
            while (true) {
                int ch = in.read();
                if (ch == -1) {
                    break;
                }
                builder.append((char) ch);
                if (builder.length() > MAX_FILE_LENGTH)
                    throw new FileLengthLimitException("The number of characters in the file exceeds the limit – " + MAX_FILE_LENGTH);
            }
            in.close();
            if (builder.toString().isEmpty()) {
                throw new FileLengthLimitException("The file is empty. Please fill it in with the data.");
            }
            return builder.toString();
        } catch (IOException e) {
            throw new FIleReadingException("File reading error\n");
        }
    }

    @Override
    public void write(String string) {
        try {
            final OutputStreamWriter streamWriter
                    = new OutputStreamWriter(
                    new FileOutputStream(this.file), StandardCharsets.UTF_8);
            streamWriter.write(string);
            streamWriter.close();
        } catch (IOException e) {
            throw new FIleWritingException("File writing error\n");
        }

    }

    @Override
    public String toString() {
        return file.toString();
    }

    public File getFile() {
        return this.file;
    }

    public File validated(File file) throws FileNotFoundException, FileReadPermissionException {
        if (!file.exists()) {
            throw new FileNotFoundException("There is no file with name " + file + '.');
        }
        if (file.isDirectory()) {
            throw new FileNotFoundException("The path you entered is a directory, not a file.");
        }
        if (!file.canRead()) {
            throw new FileReadPermissionException("No rights to read the file " + file + '.');
        }
        return file;
    }
}
