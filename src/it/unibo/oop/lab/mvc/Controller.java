package it.unibo.oop.lab.mvc;

import java.util.List;

/**
 * A controller that prints strings and has memory of the strings it printed.
 */
public interface Controller {

    /*
     * This interface must model a simple controller responsible of I/O access. It
     * considers only the standard output, and it is able to print on it.
     * 
     * Write the interface and implement it in a class in such a way that it
     * includes:
     * 
     * 1) A method for . Null values are not
     * acceptable, and an exception should be produced
     * 
     * 2) A method for getting the next string to print
     * 
     * 3) A method for getting the history of the printed strings (in form of a List
     * of Strings)
     * 
     * 4) A method that prints the current string. If the current string is unset,
     * an IllegalStateException should be thrown
     * 
     */

    /**
     * Sets the next string to print.
     * 
     * @param next
     */
    void setNextString(String next);

    /**
     * Returns the next string to be printed.
     * 
     * @return the next string to be printed.
     */
    String getNextString();

    /**
     * Returns the history of printed strings.
     * 
     * @return {@code List} of printed strings.
     */
    List<String> getHistory();

    /**
     * Prints the next string.
     */
    void printNextString();

}
