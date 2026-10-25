package eps.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads the constants (sensor ranges, MCU limits) from config.properties.
 * If the file or a key is missing, the default value given by the caller is used.
 */
public class Config {
    private static final String FILE_NAME = "config.properties";
    private static final Properties properties = new Properties();

    private Config() {
    }

    /** Call once at program start. The file is looked up in the working directory. */
    public static void load() {
        try (FileInputStream in = new FileInputStream(FILE_NAME)) {
            properties.load(in);
            System.out.println("Settings loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println(FILE_NAME + " not found, using default values");
        }
    }

    public static double getDouble(String key, double defaultValue) {
        String text = properties.getProperty(key);
        if (text == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            System.out.println("Warning: '" + key + "' is not a number in " + FILE_NAME
                    + ", using " + defaultValue);
            return defaultValue;
        }
    }

    public static int getInt(String key, int defaultValue) {
        String text = properties.getProperty(key);
        if (text == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            System.out.println("Warning: '" + key + "' is not an integer in " + FILE_NAME
                    + ", using " + defaultValue);
            return defaultValue;
        }
    }
}