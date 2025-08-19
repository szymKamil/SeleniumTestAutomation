package Parallel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        // Inicjalizacja sterownika dla bieżącego wątku
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Zapisanie sterownika w ThreadLocal
        driverThread.set(driver);
    }

    public static WebDriver getDriver() {
        // Pobranie sterownika dla bieżącego wątku
        return driverThread.get();
    }

    @AfterMethod
    public void tearDown() {
        // Pobranie sterownika, zamknięcie go i usunięcie z ThreadLocal
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}