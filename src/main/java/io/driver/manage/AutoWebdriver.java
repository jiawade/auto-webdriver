package io.driver.manage;

import io.driver.manage.browsers.Chrome;
import io.driver.manage.browsers.Edge;
import io.driver.manage.browsers.Firefox;
import io.driver.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static io.driver.utils.Helper.unzip;

@Slf4j
public abstract class AutoWebdriver extends AbstractDriverProperties {

    public AutoWebdriver() {
    }

    public static void configChromeDriver() {
        Chrome chrome = new Chrome();
        chrome.configure();
    }

    public static void configChromeDriver(Boolean clearCache) {
        Chrome chrome = new Chrome();
        chrome.clearCache().configure();
    }

    public static void configEdgeDriver() {
        Edge edge = new Edge();
        edge.configure();
    }

    public static void configEdgeDriver(Boolean clearCache) {
        Edge edge = new Edge();
        edge.clearCache().configure();
    }


    public static void configFirefoxDriver() {
        Firefox firefox = new Firefox();
        firefox.configure();
    }

    public static void configFirefoxDriver(Boolean clearCache) {
        Firefox firefox = new Firefox();
        firefox.clearCache().configure();
    }


}
