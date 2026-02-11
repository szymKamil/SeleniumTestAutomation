package Parallel;

import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class ParallelTestExample {

    private static ThreadLocal<WebDriver> driverThread2 = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
    private static ThreadLocal<LoginFormPage> loginPageTestThread = new ThreadLocal<>();


    @BeforeMethod
    public void setUp() {
        System.out.println("Inicjalizacja WebDriver dla wątku: " + Thread.currentThread().getId());
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driverThread2.set(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        waitThread.set(wait);
        loginPageTestThread.set(new LoginFormPage());
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Zamykanie WebDriver dla wątku: " + Thread.currentThread().getId());
        WebDriver driver = driverThread2.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverThread2.remove();
                waitThread.remove();
                loginPageTestThread.remove();
            }
        }
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThread2.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver jest null dla bieżącego wątku.");
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        WebDriverWait wait = waitThread.get();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait jest null dla bieżącego wątku.");
        }
        return wait;
    }

    @Test(groups = "parallel", testName = "test1")
    public void testParallel() {
        WebDriver driver = getDriver();
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        loginPageTestThread.get().useBtnVisitUs();
    }

    @Test(groups = "parallel")
    public void testBingPageTitle() {
        WebDriver driver = getDriver();
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        loginPageTestThread.get().useBtnVisitUs();
    }

    @Test
    public void testGooglePageTitle4() {
        WebDriver driver = getDriver();
        driver.get("https://www.google.com");
        getWait().until(ExpectedConditions.titleIs("Google"));
        assertEquals(driver.getTitle(), "Google");
    }

    @Test
    public void testBingPageTitle4() {
        WebDriver driver = getDriver();
        driver.get("https://www.bing.com");
        getWait().until(ExpectedConditions.titleIs("Microsoft Bing"));
        assertEquals(driver.getTitle(), "Microsoft Bing");
    }

    @Test
    public void testGooglePageTitle2() {
        WebDriver driver = getDriver();
        driver.get("https://www.google.com");
        getWait().until(ExpectedConditions.titleIs("Google"));
        assertEquals(driver.getTitle(), "Google");
    }

    @Test
    public void testBingPageTitle2() {
        WebDriver driver = getDriver();
        driver.get("https://www.bing.com");
        getWait().until(ExpectedConditions.titleIs("Microsoft Bing"));
        assertEquals(driver.getTitle(), "Microsoft Bing");
    }
}
