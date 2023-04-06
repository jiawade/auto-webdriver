import io.driver.manage.AutoWebdriver;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import org.asynchttpclient.netty.ws.NettyWebSocket;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.WebSocket;


public class DriverTest {


    public static void main(String[] args) {

        AutoWebdriver.configChromeDriver();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");
        NettyWebSocket n = new NettyWebSocket(new EmbeddedChannel(), new DefaultHttpHeaders());
        System.out.println(n.sendTextFrame("sfwerwew"));
        driver.quit();
    }


}
