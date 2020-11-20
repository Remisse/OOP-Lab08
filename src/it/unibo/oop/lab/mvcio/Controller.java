package it.unibo.oop.lab.mvcio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Interface modelling a simple controller responsible of I/O access. It considers a
 * single file at a time, and it is able to serialize objects in it.
 */
public class Controller {

    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String DEFAULT_PATH = System.getProperty("user.home");
    private static final String DEFAULT_FILE = DEFAULT_PATH + SEPARATOR + "output.txt";
    private File currentFile = new File(DEFAULT_FILE);

    /**
     * Sets a new {@code currentFile}.
     * 
     * @param file
     *          {@link File} to be set as {@code currentFile}.
     */
    public final void setCurrentFile(final File file) {
        this.currentFile = file;
    }

    /**
     * Returns the {@link File} set as {@code currentFile}.
     * 
     * @return the field {@code currentFile}.
     */
    public final File getCurrentFile() {
        return this.currentFile;
    }

    /**
     * Returns the path on disk of the file set as {@code currentFile}.
     * 
     * @return a {@code String} containing the path to {@code currentFile}.
     */
    public final String getPathOfCurrentFile() {
        return this.currentFile.getPath();
    }

    /**
     * Writes the content of {@code text} to the {@code currentFile}.
     * 
     * @param text
     *          {@code String} to be written.
     */
    public final void writeOnCurrentFile(final String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.currentFile))) {
            writer.write(text);
        }
    }

}
