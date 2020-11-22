package it.unibo.oop.lab.advanced;

import static it.unibo.oop.lab.advanced.Settings.*;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * 
     */
    public DrawNumberApp() {
        final var config = ConfigUtils.readSettings();
        this.model = new DrawNumberImpl(config.getOrDefault(MINIMUM, MINIMUM.getDefaultValue()),
                                        config.getOrDefault(MAXIMUM, MAXIMUM.getDefaultValue()),
                                        config.getOrDefault(ATTEMPTS, ATTEMPTS.getDefaultValue()));
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
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
