package io.driver.manage.browsers;

import com.jayway.jsonpath.JsonPath;
import io.driver.exception.FileNotFoundException;
import io.driver.exception.UnableToGetLocalBroserVersionException;
import io.driver.manage.AbstractDriverProperties;
import io.driver.manage.AutoWebdriver;
import io.driver.manage.SetDriver;
import io.driver.manage.enums.DriverType;
import io.driver.manage.enums.Platform;
import io.driver.utils.Helper;
import io.github.jiawade.tool.utils.HttpRequests;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
public class Chrome extends AutoWebdriver {
    private static final int arch = 32;

    public Chrome() {
        buildChromeProp();
    }

    @Override
    public Chrome clearCache() {
        Helper.cleanDirectory(getDriverSavePath(DriverType.Chrome));
        return this;
    }

    @Override
    public void configure() {
        File localDriverFile = getDriverFile(DriverType.Chrome);
        if (localDriverFile.exists()) {
            log.info("find local chrome driver, re-use it: {}", localDriverFile.getAbsolutePath());
            SetDriver.setChrome(localDriverFile.getAbsolutePath());
            return;
        }
        if (localDriverFile.exists()) {
            log.info("find local chrome driver, re-use it: {}", localDriverFile.getAbsolutePath());
            SetDriver.setChrome(localDriverFile.getAbsolutePath());
            return;
        }
        String version = findBestMatchLocalVersion(DriverType.Chrome);
        File driverFile = downloadDriver(version);
        Helper.unzip(driverFile, driverFile.getParent());

        File driver = getDownloadedDriver(driverFile.getParentFile());
        if (!driver.exists()) {
            throw new FileNotFoundException("unable to find driver in directory: " + driver.getAbsolutePath());
        }
        if (!currentPlatform.equals(Platform.Windows)) {
            Helper.exeRuntimeCommand("chmod +x " + driver.getAbsolutePath());
        }
        SetDriver.setChrome(driver.getAbsolutePath());
    }

    @Override
    public String findBestMatchLocalVersion(DriverType type) {
        String localVersion = getLocalDriverVersion(type);
        List<String> versions = getChromeVersion();
        if (versions.isEmpty()) {
            log.info("using mirror{} to download chrome driver.", chromeMirrorDownloadUrl);
            versions = getChromeVersionInMirror();
        }
        String version = Helper.findBestMatchVersion(localVersion, versions);
        if ("".equals(version)) {
            throw new IllegalArgumentException("local version chrome version is: " + localVersion + ", unable to find best match version of the list: " + versions);
        }
        return version;
    }

    @Override
    public File downloadDriver(String driverVersion) {
        if (!Pattern.matches(browserVersionPattern, driverVersion)) {
            throw new IllegalArgumentException("the driver version: " + driverVersion + " not match the version regex: " + browserVersionPattern);
        }
        String url;
        if (currentPlatform.equals(Platform.Windows)) {
            url = String.format(chromeDownloadUrl, driverVersion, currentPlatform.getName(), arch);
        } else {
            url = String.format(chromeDownloadUrl, driverVersion, currentPlatform.getName(), super.arch);
        }
        String path = getDriverSavePath(DriverType.Chrome) + File.separator + driverVersion;
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
            throw new UnableToGetLocalBroserVersionException("unable to find chrome browser version, may not install google chrome");
        }
        return versions.get(0);
    }

    private List<String> getChromeVersion() {
        List<String> versions = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new URL(chromeBaseUrl), 10000);
            Elements link = document.selectXpath("//Key");
            versions = link.stream().map(Element::text)
                    .filter(i -> i.endsWith(".zip"))
                    .filter(j -> j.contains(currentPlatform.getName()))
                    .map(k -> k.split("/")[0])
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return versions;
    }


    private List<String> getChromeVersionInMirror() {
        List<String> versions = new ArrayList<>();
        HttpRequests httpRequest = new HttpRequests();
        httpRequest.get(chromeMirrorUrl, new HashMap<>());
        List<String> versionRaw =  ((List<String>) JsonPath.parse(httpRequest.getText()).read("$..name")).stream().filter(i -> !Objects.isNull(i)).collect(Collectors.toList());
        if (versionRaw.isEmpty()) {
            return versions;
        }
        return versionRaw.stream()
                .map(i -> i.replace("/", ""))
                .filter(i -> Pattern.matches(browserVersionPattern, i))
                .collect(Collectors.toList());
    }

}
