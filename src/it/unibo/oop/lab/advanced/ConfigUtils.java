package it.unibo.oop.lab.advanced;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.unibo.oop.lab.advanced.Settings.*;

public final class ConfigUtils {

    private static final String SEP = System.getProperty("file.separator");
    private static final Path CONFIG_PATH = Paths.get("res" + SEP + "config.yml");

    /*
     * Hiding the constructor.
     */
    private ConfigUtils() {
    }

    private static String getDefaultConfigString() {
        return Stream.of(Settings.values())
                     .map(stg -> stg.getName() + ": " + stg.getDefaultValue())
                     .collect(Collectors.joining(","));
    }

    private static Map<Settings, Integer> doYAMLToMap() throws IOException {
        return Pattern.compile(",")
                      .splitAsStream(Files.lines(CONFIG_PATH)
                                          .collect(Collectors.joining(",")))
                      .map(s -> s.replace("\s", ""))
                      .map(s -> s.split(":"))
                      .collect(Collectors.toMap(s -> Settings.of(s[0]),
                                                s -> Integer.valueOf(s[1]),
                                                (x, y) -> y,
                                                () -> new EnumMap<>(Settings.class)));
    }

    public static Map<Settings, Integer> readSettings() throws IOException {
        return doYAMLToMap();
    }
}
