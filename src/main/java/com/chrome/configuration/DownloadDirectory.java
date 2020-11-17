package com.chrome.configuration;

public interface DownloadDirectory {
    public static final String downloadFolderLin = System.getProperty("java.io.tmpdir") + "/";
    public static final String downloadFolderWin = System.getProperty("java.io.tmpdir");
}
