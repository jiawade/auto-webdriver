package com.chrome.configuration;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.junit.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ChromeDriverConfigurator {
    private static String downloadBaseUrl;
    private static boolean isGoole;
    private static String platform = System.getProperty("os.name");

    public ChromeDriverConfigurator() {
    }

    static {
        if (urlConnectivity(URLS.googleDriverUrl)) {
            System.out.println("using google mirror to download chrome driver.");
            isGoole = true;
            downloadBaseUrl = URLS.googleDriverUrl;
        } else if (urlConnectivity(URLS.taobaoDriverUrl)) {
            System.out.println("using taobao mirror to download chrome driver.");
            downloadBaseUrl = URLS.taobaoDriverUrl;
            isGoole = false;
        } else {
            Assert.fail("no available chrome driver download address");
        }
    }

    public static String configureSpecificDriver(float chromeDriverVersion){
        String version = getDriverVersion(chromeDriverVersion);
        String url = downloadBaseUrl + version;
        File file;
        if ("Linux".equalsIgnoreCase(platform)) {
            file = new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver");
            if (file.exists()) {
                System.out.println("chrome driver exists at (" + file.getAbsolutePath() + ") re-using it");
                configure(file);
            } else {
                System.out.println("downloading chromedriver from url: " + url + "/chromedriver_linux64.zip");
                try {
                    FileUtils.copyURLToFile(new URL(url + "/chromedriver_linux64.zip"), new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver_linux64.zip"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("downloaded driver to: " + DownloadDirectory.downloadFolderLin+version);
                unzip(new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver_linux64.zip"), file);
                try {
                    Runtime.getRuntime().exec("chmod +x " + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                configure(file);
                System.out.println("successfully configured chrome driver");
            }
        } else {
            file = new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver.exe");
            if (file.exists()) {
                System.out.println("chrome driver exists at (" + file.getAbsolutePath() + ") re-using it");
                configure(file);
            } else {
                System.out.println("downloading chromedriver from url: " + url + "/chromedriver_win32.zip");
                try {
                    FileUtils.copyURLToFile(new URL(url + "/chromedriver_win32.zip"), new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver_win32.zip"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("downloaded driver to: " + DownloadDirectory.downloadFolderWin +version);
                unzip(new File(DownloadDirectory.downloadFolderWin +version + "/chromedriver_win32.zip"), file);
                configure(file);
                System.out.println("successfully configured chrome driver");
            }
        }
        return file.getAbsolutePath();
    }

    public static void configureAppropriateDriver() {
        String version = getAppropriateDriverVersion();
        String url = downloadBaseUrl + version;
        File file;
        if ("Linux".equalsIgnoreCase(platform)) {
            file = new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver");
            if (file.exists()) {
                System.out.println("chrome driver exists at (" + file.getAbsolutePath() + ") re-using it");
                configure(file);
            } else {
                System.out.println("downloading chromedriver from url: " + url + "/chromedriver_linux64.zip");
                try {
                    FileUtils.copyURLToFile(new URL(url + "/chromedriver_linux64.zip"), new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver_linux64.zip"));
                } catch (IOException e) {
                    Assert.fail("failed to copy file");
                }
                System.out.println("downloaded driver to: " + DownloadDirectory.downloadFolderLin+version);
                unzip(new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver_linux64.zip"), file.getAbsoluteFile());
                try {
                    Runtime.getRuntime().exec("chmod +x " + file.getAbsolutePath());
                } catch (IOException e) {
                    Assert.fail("failed to execute command");
                }
                configure(file);
                System.out.println("successfully configured chrome driver");
            }
        } else {
            file = new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver.exe");
            if (file.exists()) {
                System.out.println("chrome driver exists at (" + file.getAbsolutePath() + ") re-using it");
                configure(file);
            } else {
                System.out.println("downloading chromedriver from url: " + url + "/chromedriver_win32.zip");
                try {
                    FileUtils.copyURLToFile(new URL(url + "/chromedriver_win32.zip"), new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver_win32.zip"));
                } catch (IOException e) {
                    Assert.fail("failed to copy file");
                }
                System.out.println("downloaded driver to: " + DownloadDirectory.downloadFolderWin +version);
                unzip(new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver_win32.zip"), file);
                configure(file);
                System.out.println("successfully configured chrome driver");
            }
        }
    }

    public static String configureLatestDriver() {
        String version = getLatestDriverVersion();
        String url = downloadBaseUrl + version;
        File file;
        if ("Linux".equalsIgnoreCase(platform)) {
            file = new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            if (file.exists()) {
                System.out.println("chrome driver exists at (" + file.getAbsolutePath() + ") re-using it");
                configure(file);
            } else {
                System.out.println("downloading chromedriver from url: " + url + "/chromedriver_linux64.zip");
                try {
                    FileUtils.copyURLToFile(new URL(url + "/chromedriver_linux64.zip"), new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver_linux64.zip"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("downloaded driver to: " + DownloadDirectory.downloadFolderLin+version);
                unzip(new File(DownloadDirectory.downloadFolderLin+version + "/chromedriver_linux64.zip"), file);
                try {
                    Runtime.getRuntime().exec("chmod +x " + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                configure(file);
                System.out.println("successfully configured chrome driver");
            }
        } else {
            file = new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver.exe");
            if (file.exists()) {
                System.out.println("chrome driver exists at(" + file.getAbsolutePath() + ") re-using it");
                configure(file);
            } else {
                System.out.println("downloading chromedriver from url: " + url + "/chromedriver_win32.zip");
                try {
                    FileUtils.copyURLToFile(new URL(url + "/chromedriver_win32.zip"), new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver_win32.zip"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("downloaded driver to: " + DownloadDirectory.downloadFolderWin +version);
                unzip(new File(DownloadDirectory.downloadFolderWin +version + "\\chromedriver_win32.zip"), new File(file.getAbsolutePath()));
                configure(file);
                System.out.println("successfully configured chrome driver");
            }
        }
        return file.getAbsolutePath();
    }

    private static String getLatestDriverVersion() {
        String doc = null;
        if (isGoole){
            try {
                URL url = new URL(downloadBaseUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                doc=bin.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }

        }else {
            try {
                doc = Jsoup.parse(new URL(downloadBaseUrl), 10000).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Pattern r = Pattern.compile("(>LATEST_RELEASE_)(.*?)(<)");
        Matcher m = r.matcher(doc);
        List<String> versionList = new LinkedList<>();
        while (m.find()) {
            versionList.add(m.group(2).replace("/", ""));
        }
        return versionList.get(versionList.size() - 1);
    }

    private static String getDriverVersion(float browserVersion) {
        String doc = null;
        try {
            if (isGoole){
                URL url = new URL(downloadBaseUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                doc=bin.readLine();
            }
            else {
                doc = Jsoup.parse(new URL(downloadBaseUrl), 10000).toString().replace("\n", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern r = Pattern.compile("(>)("+browserVersion+".*?)(/)");
        Matcher m = r.matcher(doc);
        List<String> driverList = new LinkedList<>();
        while (m.find()) {
            driverList.add(m.group(2).replace("/", ""));
        }
        return driverList.get(driverList.size() - 1);
    }

    private static String getAppropriateDriverVersion() {
        BufferedReader stdInput;
        String s = null;
        if ("Linux".equalsIgnoreCase(platform)) {
            Process p = null;
            try {
                p = Runtime.getRuntime().exec("google-chrome --version");
                stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()), 8192);
                s = stdInput.readLine().split(" ")[2].split("\\.")[0];
                System.out.println("appropriate driver version is: " + s);
                return getDriverVersion(Float.parseFloat(s));
            } catch (Exception e) {
                try {
                    p = Runtime.getRuntime().exec("chrome --version");
                    stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()), 8192);
                    s = stdInput.readLine().split(" ")[1].split("\\.")[0];
                    System.out.println("appropriate driver version is: " + s);
                    return getDriverVersion(Float.parseFloat(s));
                } catch (Exception e1) {
                    System.out.println("the system has not installed chrome browser, plsease install it first and version must greater than 70.");
                    Assert.fail("the system has not installed chrome browser, plsease install it first and version must greater than 70.");
                    return null;
                }
            }
        } else {
            ArrayList<String> output = new ArrayList<>();
            Process p = null;
            String ff_value = null;
            try {
                p = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version");
            } catch (Exception e) {
                Assert.fail(e.toString() + e);
            }
            stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()), 8192);
            while (true) {
                try {
                    if ((s = stdInput.readLine()) == null) break;
                } catch (IOException e) {
                    Assert.fail("failed to write");
                }
                output.add(s);
            }
            try {
                ff_value = output.get(2);
            } catch (Exception e) {
                Assert.fail("the system has not installed chrome browser, plsease install it first and version must greater than 70.");
            }
            String version_c = ff_value.trim().split(" {3}")[2];
            System.out.println("appropriate driver version is: " + version_c);
            return getDriverVersion(Float.parseFloat(version_c.split("\\.")[0].replace(" ", "")));
        }
    }

    private static void unzip(File source, File destination) {
        System.out.println("uncompressing: " + source.getAbsolutePath());
        try {
            byte[] buffer = new byte[1024];
            ZipInputStream inputStream = new ZipInputStream(new FileInputStream(source.toString()));
            for (ZipEntry entry = inputStream.getNextEntry(); entry != null; entry = inputStream.getNextEntry()) {
                FileOutputStream fos = new FileOutputStream(destination);
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            inputStream.closeEntry();
            inputStream.close();
            System.out.println("successfully uncompress file to: " + destination.getAbsolutePath());
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    private static boolean urlConnectivity(String net) {
        boolean re;
        URL url;
        try {
            url = new URL(net.trim());
            URLConnection co = url.openConnection();
            co.setConnectTimeout(1000);
            co.connect();
            re = true;
        } catch (Exception e1) {
            re = false;
        }
        return re;
    }

    private static void configure(File file) {
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    }
}
