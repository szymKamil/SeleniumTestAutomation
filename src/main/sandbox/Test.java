import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

public class Test {

WebDriver driver;
    @BeforeClass
    public void runner() throws URISyntaxException, MalformedURLException {
       /*WebDriverManager.getInstance("Chrome")
               .create();*/
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

// Dobrą praktyką jest również dodanie tych flag
        options.addArguments("--disable-gpu"); // Czasami wymagane w środowiskach bez fizycznego GPU [9][14]
        options.addArguments("--window-size=1920,1080"); // Ustawienie okna może zapobiec problemom z layoutem [9]


        FirefoxOptions options2 = new FirefoxOptions();
        EdgeOptions options3 = new EdgeOptions();
        options.addArguments("--no-sandbox");

        URL seleniumServerUrl = new URL("http://localhost:4444/");

//        driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options); // ważne: używaj URL lub URI zależnie od wersji;
        driver = RemoteWebDriver.builder().oneOf(new EdgeOptions())
                .address(seleniumServerUrl).build();

      /*  ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver (new URL("http://localhost:4444/"), options);*/

    }





    @org.testng.annotations.Test
    public void test1() throws InterruptedException {
        driver.get("https://www.google.pl");
        Thread.sleep(Duration.ofSeconds(10));
    }
}
