package io.driver.manage;

import io.driver.exception.FailedToconfigureDriverException;
import io.driver.exception.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class SetDriver {
    public static final String chrome = "webdriver.chrome.driver";
    public static final String firefox = "webdriver.gecko.driver";
    public static final String edge = "webdriver.edge.driver";

    private SetDriver() {
    }

    public static void setChrome(String driverFile) {
        verifyFile(driverFile);
        System.setProperty(chrome, driverFile);
        if (driverFile.equals(System.getProperty(chrome))) {
            log.info("successfully configure chrome driver: {}", driverFile);
        } else {
            throw new FailedToconfigureDriverException("failed to configure the chrome driver: " + driverFile);
        }
    }

    public static void setFireFox(String driverFile) {
        verifyFile(driverFile);
        System.setProperty(firefox, driverFile);
        if (driverFile.equals(System.getProperty(firefox))) {
            log.info("successfully configure firefox driver: {}", driverFile);
        } else {
            throw new FailedToconfigureDriverException("failed to configure the firefox driver: " + driverFile);
        }
    }

    public static void setEdge(String driverFile) {
        verifyFile(driverFile);
        System.setProperty(edge, driverFile);
        if (driverFile.equals(System.getProperty(edge))) {
            log.info("successfully configure edge driver: {}", driverFile);
        } else {
            throw new FailedToconfigureDriverException("failed to configure the edge driver: " + driverFile);
        }
    }

    private static void verifyFile(String path) {
        if (!new File(path).exists()) {
            throw new FileNotFoundException("unable to find the file: " + path);
        }
    }

}
