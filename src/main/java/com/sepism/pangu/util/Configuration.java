package com.sepism.pangu.util;

import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.util.Properties;

@Log4j2
public final class Configuration {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = Configuration.class.getResourceAsStream("/project.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            log.error("Failed to load project properties from project.properties.", e);
        }
    }

    public static String get(String name) {
        return properties.getProperty(name);
    }
}
