package com.trello.api.clients;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Config {

    private static Config instance;
    private final Properties properties;

    private Config() {
        log.info("Initializing configuration...");
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                log.error("FATAL: Could not find config.properties file in resources.");
                return;
            }
            properties.load(input);
            log.info("Configuration properties loaded successfully.");
        } catch (IOException ex) {
            log.error("FATAL: IOException occurred while loading properties file.", ex);
        }
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            log.debug("Creating a new instance of Config (Singleton).");
            instance = new Config();
        }
        return instance;
    }

    public String getBaseUrl() {
        return properties.getProperty("trello.base.url");
    }

    public String getApiKey() {
        return properties.getProperty("trello.api.key");
    }

    public String getApiToken() {
        return properties.getProperty("trello.api.token");
    }
}
