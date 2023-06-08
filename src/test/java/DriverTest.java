import io.driver.manage.AutoWebdriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class DriverTest {


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
