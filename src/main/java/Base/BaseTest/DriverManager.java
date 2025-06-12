package Base.BaseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public static void initDriver(String browser, int timeoutSeconds) {
        // tutaj tworzysz WebDriver według browsera, np. przez BaseTestV1 lub bezpośrednio
        BaseTestV1 base = new BaseTestV1(browser, timeoutSeconds);
        driver.set(base.getWebDriver());
        wait.set(base.getWait());
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            wait.remove();
        }
    }
}
