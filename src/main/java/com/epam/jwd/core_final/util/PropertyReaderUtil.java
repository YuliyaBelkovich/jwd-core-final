package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() throws InvalidStateException {
        final String propertiesFileName = "src/main/resources/application.properties";

        try (InputStream propertiesStream = new FileInputStream(propertiesFileName)) {
            properties.load(propertiesStream);
        } catch (IOException e) {
            log.fatal("Properties file hasn't loaded");
            throw new InvalidStateException("properties");
        }
    }

    public static ApplicationProperties readProperties() {

        return ApplicationProperties.getInstance(properties.getProperty("inputRootDir"),
                properties.getProperty("outputRootDir"),
                properties.getProperty("crewFileName"),
                properties.getProperty("missionsFileName"),
                properties.getProperty("spaceshipsFileName"),
                Integer.parseInt(properties.getProperty("fileRefreshRate")),
                properties.getProperty("dateTimeFormat"));
    }

}

