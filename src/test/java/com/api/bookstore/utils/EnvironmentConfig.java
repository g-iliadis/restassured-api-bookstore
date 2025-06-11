package com.api.bookstore.utils;


import java.io.InputStream;
import java.util.Properties;

public class EnvironmentConfig {

    private static final Properties properties = loadConfigFile();

    private static Properties loadConfigFile() {
        String env = System.getProperty("env", "test");
        String filePath = String.format("env/%s.config.properties", env);
        InputStream input = EnvironmentConfig.class.getClassLoader().getResourceAsStream(filePath);
        if (input == null) {
            throw new RuntimeException("Environment file not found: " + filePath);
        }
        Properties props = new Properties();
        try {
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Load config file failed: " + filePath, e);
        }
        return props;
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}