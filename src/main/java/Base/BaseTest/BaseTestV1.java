package Base.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTestV1 {
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();

    WebDriver driver;
    WebDriverWait wait ;


    public BaseTestV1(String browser, int time) {
        this.driver = WebDriverManager.getInstance(browser).create();
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(time));
        driverThread.set(driver);
        waitThread.set(wait);

    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void quit() {
        if (driverThread.get() != null) {
            driver.quit();
            driverThread.remove();
            waitThread.remove();
        }
    }
}
