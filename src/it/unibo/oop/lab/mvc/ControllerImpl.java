package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a {@link Controller}.
 */
public class ControllerImpl implements Controller {

    private String next;
    private final List<String> history;

    public ControllerImpl() {
        this.history = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    public void setNextString(final String next) {
        if (next == null) {
            throw new NullPointerException();
        } else {
            this.next = next;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getNextString() {
        return this.next;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getHistory() {
        return this.history;
    }

    /**
     * {@inheritDoc}
     */
    public void printNextString() {
        if (this.next == null) {
            throw new IllegalStateException();
        } else {
            System.out.println(this.next);
            this.history.add(this.next);
        }
    }
}
