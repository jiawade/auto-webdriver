package io.driver.manage.browsers;

import io.driver.exception.FileNotFoundException;
import io.driver.exception.UnableToGetLocalBroserVersionException;
import io.driver.manage.AbstractDriverProperties;
import io.driver.manage.AutoWebdriver;
import io.driver.manage.SetDriver;
import io.driver.manage.enums.DriverType;
import io.driver.manage.enums.Platform;
import io.driver.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Firefox extends AutoWebdriver {

    public Firefox() {
        buildFirefoxProp();
    }


    @Override
    public Firefox clearCache() {
        Helper.cleanDirectory(getDriverSavePath(DriverType.Firefox));
        return this;
    }

    @Override
    public void configure() {
        File localDriverFile = getDriverFile(DriverType.Firefox);
        if (localDriverFile.exists()) {
            log.info("find local firefox driver, re-use it: {}", localDriverFile.getAbsolutePath());
            SetDriver.setFireFox(localDriverFile.getAbsolutePath());
            return;
        }
        String version = findBestMatchLocalVersion(DriverType.Firefox);
        File driverFile = downloadDriver(version);
        Helper.unzip(driverFile, driverFile.getParent());

        File driver = getDownloadedDriver(driverFile.getParentFile());
        if (!driver.exists()) {
            throw new FileNotFoundException("unable to find driver in directory: " + driver.getAbsolutePath());
        }
        if (!currentPlatform.equals(Platform.Windows)) {
            Helper.exeRuntimeCommand("chmod +x " + driver.getAbsolutePath());
        }
        SetDriver.setFireFox(driver.getAbsolutePath());
    }

    @Override
    protected String findBestMatchLocalVersion(DriverType type) {
        String localVersion = getLocalDriverVersion(type);
        String version = Helper.findBestMatchVersion(localVersion, localFireFoxDriverMapping);
        if ("".equals(version)) {
            Map<String, List<Integer>> versions = fetchNewVersionMapping();
            version = Helper.findBestMatchVersion(localVersion, versions);
            if ("".equals(version)) {
                throw new IllegalArgumentException("local version firefox version is: " + localVersion + ", unable to find best match version of the list: " + versions);
            }
        }
        return version;
    }

    @Override
    protected File downloadDriver(String driverVersion) {
        if (!Pattern.matches(browserVersionPattern, driverVersion)) {
            throw new IllegalArgumentException("the driver version: " + driverVersion + " not match the version regex: " + browserVersionPattern);
        }
        String url = "";
        String pattern = "";
        if (Helper.urlConnectivity(String.format(firefoxDownloadPattern, driverVersion, driverVersion, currentPlatform.getName()) + "32.zip")) {
            pattern = firefoxDownloadPattern;
        } else {
            pattern = firefoxMirrorDownloadPattern;
        }
        if (currentPlatform.equals(Platform.Windows)) {
            String spec = String.valueOf(arch).equals("32") ? "32.zip" : "-aarch64.zip";
            url = String.format(pattern, driverVersion, driverVersion, currentPlatform.getName()) + spec;
        } else if (currentPlatform.equals(Platform.Mac)) {
            String spec = String.valueOf(arch).equals("32") ? "os.tar.gz" : "os-aarch64.tar.gz";
            url = String.format(pattern, driverVersion, driverVersion, currentPlatform.getName(), arch) + spec;
        } else if (currentPlatform.equals(Platform.Linux)) {
            String spec = String.valueOf(arch).equals("32") ? "32.tar.gz" : "64.tar.gz";
            url = String.format(pattern, driverVersion, driverVersion, currentPlatform.getName(), arch) + spec;
        } else {
            throw new IllegalArgumentException("not support platform: " + currentPlatform);
        }
        String path = getDriverSavePath(DriverType.Firefox) + File.separator + driverVersion;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File driverFile = new File(path + File.separator + new File(url).getName());
        Helper.downloadFile(url, driverFile);
        if (!driverFile.exists()) {
            throw new FileNotFoundException("failed to download chrome driver");
        }
        return driverFile;
    }

    @Override
    public String getLocalDriverVersion(DriverType type) {
        List<String> commands = driverPlatformCommands.get(type).get(currentPlatform);
        List<String> versions = commands.stream()
                .map(Helper::exeRuntimeCommand)
                .map(AbstractDriverProperties::parseBrowserVersion)
                .distinct()
                .filter(i -> !"".equals(i))
                .collect(Collectors.toList());
        if (versions.isEmpty()) {
            throw new UnableToGetLocalBroserVersionException("unable to find firefox browser version, may not install google chrome");
        }
        return versions.get(0);
    }

    private Map<String, List<Integer>> fetchNewVersionMapping() {
        HttpRequests httpRequest = new HttpRequests();
        httpRequest.get(firefoxVersionMappingUrl, new HashMap<>());
        Document document = Jsoup.parse(httpRequest.getText());
        Elements elements = document.selectXpath("//tr/td");
        List<String> list = elements.stream().map(Element::text).collect(Collectors.toList());
        List<List<String>> rawList = groupByNumber(list, 4);
        Map<String, List<Integer>> versionListMap = rawList.stream().map(i -> {
            LinkedList<String> temp = new LinkedList<>(i);
            temp.remove(1);
            String min = temp.get(1).replaceAll("[^0-9]", "");
            temp.remove(1);
            temp.add(1, min);
            String max = temp.getLast().replaceAll("[^0-9]", "");
            temp.removeLast();
            temp.add("".equals(max) ? "0" : max);
            return temp;
        }).collect(Collectors.groupingBy(i -> i.get(0),
                Collectors.mapping(i -> i.get(1) + "," + i.get(2), Collectors.joining())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, i -> Arrays.stream(i.getValue().split(","))
                        .map(Integer::parseInt)
                        .map(v -> v == 0 ? Integer.MAX_VALUE : v)
                        .collect(Collectors.toList())));
        return versionListMap;
    }

    private <T> List<List<T>> groupByNumber(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

}
