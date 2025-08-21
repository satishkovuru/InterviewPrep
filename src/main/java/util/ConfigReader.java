package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigReader {
    private static Map<String, String> config;

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.json")) {
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(input, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }

    public static String get(String key) {
        return config.get(key);
    }
}