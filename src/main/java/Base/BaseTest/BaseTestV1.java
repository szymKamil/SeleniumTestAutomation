package Base.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTestV1 {
    // Tworzenie wielowątkowości na potrzeby izolacji testów przy parallelnym uruchomieniu.
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();

    WebDriver driver;
    WebDriverWait wait ;


    public BaseTestV1(String browser, int time) {
        this.driver = WebDriverManager.getInstance(browser).create();
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(time));
        driverThread.set(driver);
        waitThread.set(wait);

    }

    public WebDriver getDriver() {
        return driverThread.get();

    }

    public WebDriverWait getWait() {
        return waitThread.get();
    }

    public void quit() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
            waitThread.remove();
        }
    }
}
