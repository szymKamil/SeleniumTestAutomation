package Base.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTestV1 {

    WebDriver webDriver;
    WebDriverWait wait ;



    public BaseTestV1(@Optional("Chrome")String browser, long time) {
        this.webDriver = WebDriverManager.getInstance(browser).create();
        this.wait = new WebDriverWait(webDriver,Duration.ofSeconds(time));
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
