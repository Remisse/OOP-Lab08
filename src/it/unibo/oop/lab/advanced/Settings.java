package it.unibo.oop.lab.advanced;

public enum Settings {

    MINIMUM("minimum", 10),
    MAXIMUM("maximum", 60),
    ATTEMPTS("attempts", 5);

    private final String name;
    private final int defaultValue;

    Settings(final String name, final int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return this.name;
    }

    public int getDefaultValue() {
        return this.defaultValue;
    }

    public static Settings of(final String name) {
        for (final Settings setting : values()) {
            if (setting.name.equals(name)) {
                return setting;
            }
        }
        throw new IllegalArgumentException(name);
    }

    public String toString() {
        return this.name;
    }
}
