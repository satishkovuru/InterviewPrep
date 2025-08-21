package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility class for reading configuration values from a JSON file.
 * Loads the configuration from 'config.json' on the classpath at startup.
 */
public class ConfigReader {
    /** Holds the configuration key-value pairs loaded from the JSON file. */
    private static Map<String, String> config;

    // Static block to load configuration at class initialization
    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.json")) {
            final ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(input, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }

    /**
     * Retrieves the value for the specified configuration key.
     *
     * @param key the configuration key to look up
     * @return the value associated with the key, or null if not found
     */
    public static String get(String key) {
        return config.get(key);
    }
}