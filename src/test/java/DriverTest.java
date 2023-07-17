import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;


public class DriverTest {


    public static void main(String[] args) {
        //for chrome
//        AutoWebdriver.configEdgeDriver();
        //for firefox AutoWebdriver.configFirefoxDriver();
        //for edge AutoWebdriver.configEdgeDriver();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");
        driver.quit();
    }


}
