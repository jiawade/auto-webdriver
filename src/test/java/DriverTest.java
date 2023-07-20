import io.driver.manage.AutoWebdriver;
import io.driver.manage.SetDriver;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.edge.EdgeDriver;


public class DriverTest {

    @Test
    public void testDownloadChrome(){
        AutoWebdriver.configChromeDriver();
        Assert.assertNotNull(System.getProperty(SetDriver.chrome));
    }

    @Test
    public void testDownloadChromeClearCache(){
        AutoWebdriver.configChromeDriver(true);
        Assert.assertNotNull(System.getProperty(SetDriver.chrome));
    }

    @Test
    public void testDownloadFirefox(){
        AutoWebdriver.configFirefoxDriver();
        Assert.assertNotNull(System.getProperty(SetDriver.firefox));
    }

    @Test
    public void testDownloadFirefoxClearCache(){
        AutoWebdriver.configFirefoxDriver(true);
        Assert.assertNotNull(System.getProperty(SetDriver.firefox));
    }


    @Test
    public void testDownloadEdge(){
        AutoWebdriver.configEdgeDriver();
        Assert.assertNotNull(System.getProperty(SetDriver.edge));
    }

    @Test
    public void testDownloadEdgeClearCache(){
        AutoWebdriver.configEdgeDriver(true);
        Assert.assertNotNull(System.getProperty(SetDriver.edge));
        new EdgeDriver().close();
    }


}
