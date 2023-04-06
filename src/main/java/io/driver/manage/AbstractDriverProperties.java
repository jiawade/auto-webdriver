package io.driver.manage;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import io.driver.exception.FileNotFoundException;
import io.driver.exception.UnableToCreateDirectoryException;
import io.driver.manage.enums.DriverType;
import io.driver.manage.enums.Platform;
import io.driver.utils.Config;
import io.driver.utils.GsonUtils;
import io.driver.utils.Helper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Getter
public abstract class AbstractDriverProperties {
    protected int arch;
    protected Platform currentPlatform;
    protected String chromeBaseUrl;
    protected String chromeDownloadUrl;
    protected String chromeMirrorUrl;
    protected String chromeMirrorDownloadUrl;
    protected String firefoxVersionMappingUrl;
    protected String firefoxVersionMappingsRelationship;
    protected String firefoxDownloadUrl;
    protected String firefoxDownloadPattern;
    protected String firefoxMirrorDownloadPattern;
    protected String edgeBaseUrl;
    protected String edgeDownloadUrl;
    protected Map<String, List<Integer>> localFireFoxDriverMapping;
    protected Map<DriverType, Map<Platform, List<String>>> driverPlatformCommands = new EnumMap<>(DriverType.class);
    protected static final String browserVersionPattern = "[\\d+.\\d+]+";

    protected AbstractDriverProperties() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            currentPlatform = Platform.Windows;
        } else if (osName.contains("linux")) {
            currentPlatform = Platform.Linux;
        } else if (osName.contains("mac")) {
            currentPlatform = Platform.Mac;
        } else {
            throw new IllegalArgumentException("not supported os type:" + osName);
        }
        Properties properties = System.getProperties();
        String propertiesValue = properties.getProperty("os.arch");
        arch = (propertiesValue.equals("x86") ? 32 : 64);
        chromeBaseUrl = Config.getString("chrome.google");
        chromeDownloadUrl = Config.getString("chrome.googleDownloadUrlPattern");
        chromeMirrorUrl = Config.getString("chrome.mirror");
        chromeMirrorDownloadUrl = Config.getString("chrome.mirrorDownloadUrlPattern");
        firefoxVersionMappingUrl = Config.getString("fireox.version.mappinginfo");
        firefoxVersionMappingsRelationship = Config.getString("firefox.version.localmappinginfo");
        firefoxDownloadUrl = Config.getString("firefox.geckoDriverUrl");
        firefoxDownloadPattern = Config.getString("firefox.downloadUrlPattern");
        firefoxMirrorDownloadPattern = Config.getString("firefox.mirrorDownloadUrlPattern");
        edgeBaseUrl = Config.getString("edge.edgeDriverUrl");
        edgeDownloadUrl = Config.getString("edge.edgeDownloadUrlPattern");
    }

    protected abstract String findBestMatchLocalVersion(DriverType type);

    protected abstract File downloadDriver(String driverVersion);

    public abstract void configure();

    public abstract String getLocalDriverVersion(DriverType type);

    public abstract AbstractDriverProperties clearCache();

    protected String getDriverSavePath(DriverType type) {
        String tmpdir = System.getProperty("java.io.tmpdir");
        String driverDir = (currentPlatform.equals(Platform.Windows) || currentPlatform.equals(Platform.Mac)) ? tmpdir + "webdriver" : tmpdir + File.separator + "webdriver";
        switch (type) {
            case Chrome:
                return driverDir + File.separator + "chrome";
            case Firefox:
                return driverDir + File.separator + "firefox";
            case Edge:
                return driverDir + File.separator + "edge";
            default:
                throw new IllegalArgumentException("no such type: " + type);
        }
    }

    protected File getDownloadedDriver(File file) {
        if (!file.exists()) {
            throw new FileNotFoundException("unable to find the directory: " + file.getAbsolutePath());
        }
        List<File> files = new ArrayList<>(Helper.listFiles(file.getAbsolutePath()));
        if (files.isEmpty()) {
            return new File("");
        }
        switch (currentPlatform) {
            case Windows:
                List<File> fileList = files.stream()
                        .filter(i -> "exe".equals(FilenameUtils.getExtension(i.getName())))
                        .filter(i -> i.getName().contains("driver"))
                        .collect(Collectors.toList());
                if (fileList.isEmpty()) {
                    return new File("");
                }
                return fileList.get(0);
            case Linux:
            case Mac:
                List<File> fileListLinuxOrMac = files.stream()
                        .filter(i -> "".equals(FilenameUtils.getExtension(i.getName())))
                        .filter(i -> i.getName().contains("driver"))
                        .collect(Collectors.toList());
                if (fileListLinuxOrMac.isEmpty()) {
                    return new File("");
                }
                return fileListLinuxOrMac.get(0);
            default:
                throw new IllegalArgumentException("no such type: " + currentPlatform);
        }
    }

    private List<String> getSavedDriverVersions(String path) {
        if (!new File(path).exists()) {
            try {
                FileUtils.forceMkdir(new File(path));
            } catch (IOException e) {
                log.error(e.toString(), e);
                throw new UnableToCreateDirectoryException("unable to create the directory: " + path);
            }
        }
        File[] directories = new File(path).listFiles(File::isDirectory);
        if (Objects.isNull(directories)) {
            throw new FileNotFoundException("no directory display in the path: " + path);
        }
        return Arrays.stream(directories)
                .map(File::getName)
                .filter(i -> Pattern.matches(browserVersionPattern, i))
                .collect(Collectors.toList());
    }

    protected File getDriverFile(DriverType type) {
        String savedPath = getDriverSavePath(type);
        List<String> driverVersionList = getSavedDriverVersions(savedPath);
        if (driverVersionList.isEmpty()) {
            return new File("");
        }
        String matchVersion = "";
        if (DriverType.Firefox.equals(type)) {
            matchVersion = Helper.findBestMatchVersion(getLocalDriverVersion(type), localFireFoxDriverMapping);
        } else {
            matchVersion = Helper.findBestMatchVersion(getLocalDriverVersion(type), driverVersionList);
        }
        if ("".equals(matchVersion)) {
            return new File("");
        }
        return getDownloadedDriver(new File(savedPath + File.separator + matchVersion));
    }

    protected void buildChromeProp() {
        List<String> windowsCommand = Lists.newArrayList(
                Config.getString("command.chrome.windows.01"),
                Config.getString("command.chrome.windows.02"),
                Config.getString("command.chrome.windows.03"),
                Config.getString("command.chrome.windows.04"));
        List<String> linuxCommand = Lists.newArrayList(Config.getString("command.chrome.linux"));
        List<String> macCommand = Lists.newArrayList(Config.getString("command.chrome.mac"));

        Map<Platform, List<String>> platformListMap = new EnumMap<>(Platform.class);
        platformListMap.put(Platform.Windows, windowsCommand);
        platformListMap.put(Platform.Linux, linuxCommand);
        platformListMap.put(Platform.Mac, macCommand);
        driverPlatformCommands.put(DriverType.Chrome, platformListMap);
    }

    protected void buildFirefoxProp() {
        URL url = AbstractDriverProperties.class.getClassLoader().getResource("geckodriver_mapping.json");
        String versionJson = Helper.readFileToString(FileUtils.toFile(url));
        Type typeToken = new TypeToken<Map<String, List<Integer>>>() {
        }.getType();
        localFireFoxDriverMapping = (Map<String, List<Integer>>) GsonUtils.parseToObject(versionJson, typeToken);
        List<String> windowsCommand = Lists.newArrayList(
                Config.getString("command.firefox.windows.01"),
                Config.getString("command.firefox.windows.02"),
                Config.getString("command.firefox.windows.03"));
        List<String> linuxCommand = Lists.newArrayList(Config.getString("command.firefox.linux"));
        List<String> macCommand = Lists.newArrayList(Config.getString("command.firefox.mac"));

        Map<Platform, List<String>> platformListMap = new EnumMap<>(Platform.class);
        platformListMap.put(Platform.Windows, windowsCommand);
        platformListMap.put(Platform.Linux, linuxCommand);
        platformListMap.put(Platform.Mac, macCommand);
        driverPlatformCommands.put(DriverType.Firefox, platformListMap);
    }

    protected void buildEdgeProp() {
        List<String> windowsCommand = Lists.newArrayList(
                Config.getString("command.edge.windows.01"),
                Config.getString("command.edge.windows.02"),
                Config.getString("command.edge.windows.03"));
        List<String> linuxCommand = Lists.newArrayList(Config.getString("command.edge.linux"));
        List<String> macCommand = Lists.newArrayList(Config.getString("command.edge.mac"));

        Map<Platform, List<String>> platformListMap = new EnumMap<>(Platform.class);
        platformListMap.put(Platform.Windows, windowsCommand);
        platformListMap.put(Platform.Linux, linuxCommand);
        platformListMap.put(Platform.Mac, macCommand);
        driverPlatformCommands.put(DriverType.Edge, platformListMap);
    }

    protected static List<String> getAllMatch(String regex, String text) {
        Matcher m = Pattern.compile(regex).matcher(text);
        List<String> find = new ArrayList<>();
        while (m.find()) {
            find.add(m.group());
        }
        return find;
    }

    protected static String parseBrowserVersion(String text) {
        List<String> matches = getAllMatch(browserVersionPattern, text);
        return !matches.isEmpty() ? matches.get(0) : "";
    }

    protected static String parseBrowserVersion(Process process) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 8192);
        String text = bufferedReader.lines().filter(i -> !"".equals(i)).collect(Collectors.joining());
        List<String> matches = getAllMatch(browserVersionPattern, text);
        return !matches.isEmpty() ? matches.get(0) : "";
    }


}
