package VanillaSelenium.TheInternetHerokuAPP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static io.github.bonigarcia.wdm.WebDriverManager.isOnline;
import static org.assertj.core.api.Assumptions.assumeThat;

public class VanillaTests {

    private static final Logger log = LoggerFactory.getLogger(VanillaTests.class);
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        URI uri = URI.create("http://192.168.1.108:4444");

        ClientConfig clientConfig = ClientConfig.defaultConfig()
                .readTimeout(Duration.ofSeconds(30))
                .baseUrl(uri.toURL());

        assumeThat(isOnline(uri.toString())).isTrue();
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        driver = RemoteWebDriver.builder().oneOf( new ChromeOptions().addArguments("--headless=chrome")
                .addArguments("--no-sandbox")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("--disable-gpu"), new FirefoxOptions()).addAlternative(firefoxOptions).config(clientConfig).address(uri).build();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    }

    @Test
    public void connectionTest(){
        driver.get("https://the-internet.herokuapp.com/");
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.heading")));
        heading.isDisplayed();
        String headingText = heading.getText();
        log.info("Strona ma nagłówek o nazwie: {}", headingText);
        String title = driver.getTitle();
        log.info("Strona ma tytuł: {}", title);

    }

    @AfterTest
    public void quitTest(){
        assert driver != null;
        driver.quit();
    }







}
