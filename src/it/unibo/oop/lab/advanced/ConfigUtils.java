package it.unibo.oop.lab.advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ConfigUtils {

    private static final String SPLITTER = ":";
    private static final String SEP = System.getProperty("file.separator");
    private static final Path CONFIG_PATH = Paths.get("res" + SEP + "config.yml");

    /*
     * Hiding the constructor.
     */
    private ConfigUtils() {
    }

    private static List<String> getDefaultConfig() {
        return Stream.of(Settings.values())
                     .map(stg -> stg.getName() + SPLITTER + "\s" + stg.getDefaultValue())
                     .collect(Collectors.toList());
    }
    
    private static void generateDefaultConfigFile() throws IOException {
        Files.write(CONFIG_PATH, getDefaultConfig(), StandardOpenOption.CREATE_NEW,
                                                     StandardOpenOption.WRITE);
    }

    private static Map<Settings, Integer> doYAMLToMap() throws IOException {
        return Files.lines(CONFIG_PATH)
                    .map(s -> s.replace("\s", "")) //Delete all whitespaces
                    .map(s -> s.split(SPLITTER))
                    .collect(Collectors.toMap(s -> Settings.of(s[0]),
                                              s -> Integer.valueOf(s[1]),
                                              (x, y) -> y, //If there's a duplicate setting, get the last occurrence
                                              () -> new EnumMap<>(Settings.class)));
    }
    
    public static Map<Settings, Integer> readConfig() throws IOException {
        if (Files.notExists(CONFIG_PATH)) {
            generateDefaultConfigFile();
        }
        return doYAMLToMap();
    }
}
