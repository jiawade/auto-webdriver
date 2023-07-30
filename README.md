
# auto-webdriver #

auto-webdriver is a browser driver configurator for selenium

## Installation

#### Maven
````xml
<dependency>
    <groupId>io.github.jiawade</groupId>
    <artifactId>auto-webdriver</artifactId>
    <version>0.0.3</version>
</dependency>
````

#### Gradle
````gradle
compile 'io.github.jiawade:auto-webdriver:0.0.3'
````


## Usage Example
````java
import io.github.adapter.TestngExtentReportListener;
import org.testng.annotations.Test;


public class Test {

    public static void main(String[] args) {
        //for chrome
        AutoWebdriver.configChromeDriver();
        //for firefox AutoWebdriver.configFirefoxDriver();
        //for edge AutoWebdriver.configEdgeDriver();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");
        driver.quit();
        }

}
````

## Submitting Issues
For any issues or requests, please submit [here](https://github.com/jiawade/auto-webdriver/issues)
