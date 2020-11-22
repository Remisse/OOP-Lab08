package it.unibo.oop.lab.advanced;

import static it.unibo.oop.lab.advanced.Settings.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * 
     */
    public DrawNumberApp() {
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();

        Map<Settings, Integer> config;
        try {
            config = ConfigUtils.readConfig();
        } catch (IOException e) {
            e.printStackTrace();
            this.view.displayError(e.toString());
            config = Collections.emptyMap();
        }
        this.model = new DrawNumberImpl(config.getOrDefault(MINIMUM, MINIMUM.getDefaultValue()),
                                        config.getOrDefault(MAXIMUM, MAXIMUM.getDefaultValue()),
                                        config.getOrDefault(ATTEMPTS, ATTEMPTS.getDefaultValue()));
    }

    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.view.result(result);
        } catch (IllegalArgumentException e) {
            this.view.numberIncorrect();
        } catch (AttemptsLimitReachedException e) {
            view.limitsReached();
        }
    }

    public void resetGame() {
        this.model.reset();
    }

    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) {
        new DrawNumberApp();
    }

}
