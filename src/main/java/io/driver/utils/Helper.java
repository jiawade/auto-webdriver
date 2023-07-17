package io.driver.utils;

import io.driver.exception.ExecuteRuntimeCommandErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

@Slf4j
public class Helper {
    private Helper() {

    }

    public static List<File> listFiles(String filePath) {
        try {
            return Files.walk(Paths.get(filePath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new ArrayList<>();
    }

    public static Process exeRuntimeCommand(String command) {
        try {
            return Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            log.error(e.toString(), e);
            throw new ExecuteRuntimeCommandErrorException("unable to exectue the command: " + command);
        }
    }

    public static void unzip(File file, String destinationDir) {
        log.info("uncompressing file: {} to the directory: {}", file, destinationDir);
        if (file.getName().endsWith(".tar.gz")) {
            try {
                Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
                archiver.extract(file, new File(destinationDir));
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
            return;
        }
        try {
            java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(destinationDir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = new FileOutputStream(entryDestination)) {
                        IOUtils.copy(in, out);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }


    public static void downloadFile(String url, File destination) {
        log.info("downloading driver: {} to directory: {}", url, destination.getParent());
        try {
            FileUtils.copyURLToFile(new URL(url), destination);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    public static boolean urlConnectivity(String net) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(net).openConnection();
            connection.setConnectTimeout(3000);
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            log.error("the url: {} can not be connected", net);
            return false;
        }
    }

    public static void cleanDirectory(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    public static String readFileToString(File file) {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return "";
    }

    public static String findBestMatchVersion(String currentVersion, Map<String, List<Integer>> versionList) {
        if ("".equals(currentVersion)) {
            throw new IllegalArgumentException("current version is empty");
        }
        if (versionList.isEmpty()) {
            throw new IllegalArgumentException("version list is empty");
        }
        int mainVersion = Integer.parseInt(currentVersion.split("\\.")[0]);
        List<Integer> minList = versionList.values().stream().map(integers -> integers.get(0)).collect(Collectors.toList());
        if (mainVersion < Collections.min(minList)) {
            throw new IllegalArgumentException("the version number of the Firefox browser must be greater than or equal to 52");
        }
        Map<String, List<Integer>> filtered = versionList.entrySet().stream().filter(i -> {
            List<Integer> values = i.getValue();
            return mainVersion >= values.get(0) && mainVersion <= values.get(1);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (filtered.isEmpty()){
            return "";
        }
        Integer minSize = Collections.min(filtered.keySet().stream().map(i -> i.split("\\.").length).collect(Collectors.toList()));
        Set<String> allVersions = new HashSet<>(filtered.keySet());
        for (int i = 0; i < minSize; i++) {
            int var = i;
            Integer max = Collections.max(allVersions.stream().map(j -> Integer.parseInt(j.split("\\.")[var])).collect(Collectors.toList()));
            List<String> newList = allVersions.stream().filter(j -> j.split("\\.")[var].equals(String.valueOf(max))).collect(Collectors.toList());
            allVersions.clear();
            allVersions.addAll(newList);
        }
        return allVersions.iterator().next();
    }

    public static String findBestMatchVersion(String currentVersion, List<String> versionList) {
        if ("".equals(currentVersion)) {
            throw new IllegalArgumentException("current version is empty");
        }
        if (versionList.isEmpty()) {
            throw new IllegalArgumentException("version list is empty");
        }
        List<String> mainVersions = versionList.stream().filter(i -> i.split("\\.")[0].equals(currentVersion.split("\\.")[0])).collect(Collectors.toList());
        if (mainVersions.isEmpty()) {
            return "";
        }
        List<Long> subList = Arrays.stream(currentVersion.split("\\.")).map(Long::parseLong).collect(Collectors.toList());
        for (int i = 0; i < subList.size(); i++) {
            long mainIndex = subList.get(i);
            List<String> equals = new ArrayList<>();
            List<String> others = new ArrayList<>();
            for (String s : versionList) {
                long versionIndex = Long.parseLong(s.split("\\.")[i]);
                if (versionIndex == mainIndex) {
                    equals.add(s);
                } else {
                    others.add(s);
                }
            }

            if (!equals.isEmpty()) {
                versionList = equals;
            } else {
                int va = i;
                Long max = others.stream().map(m -> Long.parseLong(m.split("\\.")[va])).max(Comparator.naturalOrder()).orElseThrow(() -> new NoSuchElementException("No value present"));
                versionList = others.stream().filter(b -> {
                    Long factor = Long.parseLong(b.split("\\.")[va]);
                    return factor.equals(max);
                }).collect(Collectors.toList());
            }

        }
        return versionList.isEmpty() ? "" : versionList.get(0);
    }


}
