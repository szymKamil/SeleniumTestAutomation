import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.Cookie;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Driver;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class Test {

WebDriver driver;
    DevTools devTools;

    @BeforeClass
    public void runner() throws URISyntaxException, MalformedURLException {
       /*WebDriverManager.getInstance("Chrome")
               .create();*/
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");

// Dobrą praktyką jest również dodanie tych flag
        options.addArguments("--disable-gpu"); // Czasami wymagane w środowiskach bez fizycznego GPU [9][14]
        options.addArguments("--window-size=1920,1080"); // Ustawienie okna może zapobiec problemom z layoutem [9]


        FirefoxOptions options2 = new FirefoxOptions();
        EdgeOptions options3 = new EdgeOptions();


        URL seleniumServerUrl = new URL("https://www.google.pl");

//        driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options); // ważne: używaj URL lub URI zależnie od wersji;
//        driver = RemoteWebDriver.builder().oneOf(new ChromeOptions())
//                .address(seleniumServerUrl).build();

         driver = new ChromeDriver(options);





      /*  ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver (new URL("http://localhost:4444/"), options);*/

    }



    @org.testng.annotations.Test
    public void test1() throws InterruptedException {
        driver.get("https://www.google.pl");
        Thread.sleep(Duration.ofSeconds(10));
        devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        List<Cookie> cookies = devTools.send(Network.getCookies(Optional.empty()));
        Logger logger = LoggerFactory.getLogger("Logger");
        for (Cookie cookie : cookies){
            logger.info("Ciasteczko: {}, {}, {}", cookie.getName(), cookie.getDomain(), cookie.getValue());
        }
        driver.quit();

    }
}
