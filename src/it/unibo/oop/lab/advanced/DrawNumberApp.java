package it.unibo.oop.lab.advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Map.entry;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * 
     */
    public DrawNumberApp() {
        final var config = ConfigUtilities.readSettings();
        this.model = new DrawNumberImpl(config.get("minimum"), config.get("maximum"), config.get("attempts"));
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
    }

    private static class ConfigUtilities {

        private static final String SEP = System.getProperty("file.separator");
        private static final Path CONFIG_PATH = Paths.get("res" + SEP + "config.yml");
        private static final Map<String, Integer> DEFAULT_CONF = Map.ofEntries(entry("minimum", 10),
                                                                               entry("maximum", 60),
                                                                               entry("attempts", 5));

        private static String getDefaultConfigString() {
            return DEFAULT_CONF.entrySet().stream()
                                          .map(entry -> entry.getKey() + ": " + entry.getValue())
                                          .collect(Collectors.joining(","));
        }

        private static Map<String, Integer> doYAMLToMap() {
            String settings;
            try {
                settings = Files.lines(CONFIG_PATH)
                                .collect(Collectors.joining(","));
            } catch (IOException e) {
                System.err.println("Config file inaccessible. Reverting to default settings.");
                settings = getDefaultConfigString();
            }
            return Pattern.compile(",")
                          .splitAsStream(settings)
                          .map(s -> s.split(":"))
                          .collect(Collectors.toMap(s -> s[0].trim(),
                                                    s -> Integer.valueOf(s[1].trim())));
        }

        /*
         * TODO: Check whether the map contains an entry for each setting.
         * For each missing setting, use the default values stored in DEFAULT_CONF.
         */
        private static Map<String, Integer> readSettings() {
            return doYAMLToMap();
        }
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
