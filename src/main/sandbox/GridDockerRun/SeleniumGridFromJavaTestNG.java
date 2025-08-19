package GridDockerRun;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.openqa.selenium.grid.Main;

import org.testng.annotations.*;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SeleniumGridFromJavaTestNG {

    private static final Logger log = LoggerFactory.getLogger(SeleniumGridFromJavaTestNG.class);
    private static URL seleniumServerUrl;
    private WebDriver driver;



    @BeforeSuite
    public void setupSuite() throws MalformedURLException {
        int port = PortProber.findFreePort();

        // Przygotowanie ChromeDriver
        WebDriverManager.chromedriver().setup();

        try {
            Main.main(new String[]{"standalone", "--port", String.valueOf(port)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        seleniumServerUrl = new URL(String.format("http://localhost:%d", port));
        System.out.println("Grid uruchamiany jest na: " + seleniumServerUrl);
        log.info("Selenium uruchamiane jest na {}", seleniumServerUrl);
    }

    @BeforeMethod
    public void setup() {
        driver = new RemoteWebDriver(seleniumServerUrl, new ChromeOptions());

    }

    @Test
    public void testGoogle() {
        driver.get("https://www.google.com");
        Assert.assertEquals(driver.getTitle(), "Google");
        try {
            Thread.sleep(Duration.ofSeconds(30));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
