package io.driver.utils;

import io.driver.exception.ReadConfigurationFailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Config {


    private static PropertiesConfiguration config;

    static {
        try {
            URL url = Config.class.getClassLoader().getResource("autowebdriver.properties");
            if (url == null) {
                throw new RuntimeException("unable to find resource: autowebdriver.properties");
            }
            config = new PropertiesConfiguration(url);
            config.setEncoding("UTF-8");
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            throw new ReadConfigurationFailException("load autowebdriver.properties error: " + e.toString());
        }
    }


    public static String[] getStringArray(final String propertyName) {
        return config.getStringArray(propertyName);
    }

    public static String getString(final String propertyName) {
        return config.getString(propertyName);
    }

    public static List<String> getList(final String propertyName) {
        List<Object> list = config.getList(propertyName);
        List<String> listStr = new ArrayList<>();

        for (Object obj : list) {
            String str = String.valueOf(obj);
            listStr.add(str);
        }
        return listStr;
    }

    public static String getString(final String propertyName, final String defaultValue) {
        return config.getString(propertyName, defaultValue);
    }

    public static int getInt(final String propertyName) {
        return config.getInt(propertyName);
    }

    public static int getInt(final String propertyName, final int defaultValue) {
        return config.getInt(propertyName, defaultValue);
    }

    public static float getFloat(final String propertyName) {
        return config.getFloat(propertyName);
    }

    public static float getFloat(final String propertyName, final int defaultValue) {
        return config.getFloat(propertyName, defaultValue);
    }

    public static double getDouble(final String propertyName) {
        return config.getDouble(propertyName);
    }

    public static double getDouble(final String propertyName, final int defaultValue) {
        return config.getDouble(propertyName, defaultValue);
    }

    public static boolean getBoolean(final String propertyName) {
        return config.getBoolean(propertyName);
    }

    public static boolean getBoolean(final String propertyName, final boolean defaultValue) {
        return config.getBoolean(propertyName, defaultValue);
    }

    public static synchronized void setProperty(String propertyName, String propertyValue) {
        try {
            config.setProperty(propertyName, propertyValue);
            config.save();
        } catch (ConfigurationException e) {
            log.error("Exception occured while writing autowebdriver.properties.", e);
        }
    }
}
