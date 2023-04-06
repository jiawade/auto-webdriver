package io.driver.manage.browsers;

import io.driver.exception.FileNotFoundException;
import io.driver.exception.UnableToGetLocalBroserVersionException;
import io.driver.manage.AbstractDriverProperties;
import io.driver.manage.AutoWebdriver;
import io.driver.manage.SetDriver;
import io.driver.manage.enums.DriverType;
import io.driver.manage.enums.Platform;
import io.driver.utils.Helper;
import io.driver.utils.HttpRequests;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
public class Edge extends AutoWebdriver {

    public Edge(){
        buildEdgeProp();
    }

    @Override
    public Edge clearCache() {
        Helper.cleanDirectory(getDriverSavePath(DriverType.Edge));
        return this;
    }

    @Override
    public void configure() {
        File localDriverFile = getDriverFile(DriverType.Edge);
        if (localDriverFile.exists()) {
            log.info("find local edge driver, re-use it: {}", localDriverFile.getAbsolutePath());
            SetDriver.setEdge(localDriverFile.getAbsolutePath());
            return;
        }
        String version = findBestMatchLocalVersion(DriverType.Edge);
        File driverFile = downloadDriver(version);
        Helper.unzip(driverFile, driverFile.getParent());

        File driver = getDownloadedDriver(driverFile.getParentFile());
        if (!driver.exists()) {
            throw new FileNotFoundException("unable to find driver in directory: " + driver.getAbsolutePath());
        }
        if (!currentPlatform.equals(Platform.Windows)) {
            Helper.exeRuntimeCommand("chmod +x " + driver.getAbsolutePath());
        }
        SetDriver.setEdge(driver.getAbsolutePath());
    }

    @Override
    public String findBestMatchLocalVersion(DriverType type) {
        String localVersion = getLocalDriverVersion(type);
        List<String> versions = getEdgeVersion();
        String version = Helper.findBestMatchVersion(localVersion, versions);
        if ("".equals(version)) {
            throw new IllegalArgumentException("local version chrome version is: " + localVersion + ", unable to find best match version of the list: " + versions);
        }
        return version;
    }

    @Override
    protected File downloadDriver(String driverVersion) {
        if (!Pattern.matches(browserVersionPattern, driverVersion)) {
            throw new IllegalArgumentException("the driver version: " + driverVersion + " not match the version regex: " + browserVersionPattern);
        }
        String url;
        if (currentPlatform.equals(Platform.Windows)) {
            url = String.format(edgeDownloadUrl, driverVersion, currentPlatform.getName(), arch);
        } else {
            url = String.format(edgeDownloadUrl, driverVersion, currentPlatform.getName(), arch);
        }
        String path = getDriverSavePath(DriverType.Edge) + File.separator + driverVersion;
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

    private List<String> getEdgeVersion() {
        HttpRequests httpRequest = new HttpRequests();
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "<calculated when request is sent>");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        httpRequest.get(edgeBaseUrl, headers);
        List<String> versions = new ArrayList<>();
        try {
            Document document = Jsoup.parse(httpRequest.getText());
            Elements link = document.selectXpath("//name");
            versions = link.stream().map(Element::text)
                    .filter(i -> i.endsWith(".zip"))
                    .filter(j -> j.contains(currentPlatform.getName()))
                    .map(k -> k.split("/")[0])
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return versions;
    }
}
