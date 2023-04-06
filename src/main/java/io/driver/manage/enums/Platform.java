package io.driver.manage.enums;

public enum Platform {
    Windows("win"), Linux("linux"), Mac("mac");

    private String name;

    Platform(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
