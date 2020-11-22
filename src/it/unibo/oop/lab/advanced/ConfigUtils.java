package it.unibo.oop.lab.advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static it.unibo.oop.lab.advanced.Settings.*;

public final class ConfigUtils {

    private static final String SEP = System.getProperty("file.separator");
    private static final Path CONFIG_PATH = Paths.get("res" + SEP + "config.yml");
    private static final Map<Settings, Integer> DEFAULT_CONF = new EnumMap<>(Settings.class);
    {
        DEFAULT_CONF.put(MINIMUM, MINIMUM.getDefaultValue());
        DEFAULT_CONF.put(MAXIMUM, MAXIMUM.getDefaultValue());
        DEFAULT_CONF.put(ATTEMPTS, ATTEMPTS.getDefaultValue());
    }

    /*
     * Hiding the constructor.
     */
    private ConfigUtils() {
    }

    private static String getDefaultConfigString() {
        return DEFAULT_CONF.entrySet().stream()
                                      .map(entry -> entry.getKey() + ": " + entry.getValue())
                                      .collect(Collectors.joining(","));
    }

    private static Map<Settings, Integer> doYAMLToMap() {
        String savedConf;
        try {
            savedConf = Files.lines(CONFIG_PATH)
                             .collect(Collectors.joining(","));
        } catch (IOException e) {
            System.err.println("Failed to access saved config. Reverting to default settings.");
            savedConf = getDefaultConfigString();
        }
        return Pattern.compile(",")
                      .splitAsStream(savedConf)
                      .map(s -> s.split(":"))
                      .collect(Collectors.toMap(s -> Settings.of(s[0].trim()),
                                                s -> Integer.valueOf(s[1].trim()),
                                                (x, y) -> y,
                                                () -> new EnumMap<>(Settings.class)));
    }

    public static Map<Settings, Integer> readSettings() {
        return doYAMLToMap();
    }
}
