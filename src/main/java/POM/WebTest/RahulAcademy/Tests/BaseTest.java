package POM.WebTest.RahulAcademy.Tests;


import static Base.BaseTest.DriverFactoryV1.getDriver;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.ShopTest.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopLoginPageForm;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BaseTest {


    protected final URI uri = null; /*= URI.create("http://192.168.1.108:4444")*/;
    private final String formTestPage = "https://rahulshettyacademy.com/locatorspractice/";
    private final String shopLoginPage = "https://rahulshettyacademy.com/loginpagePractise/";
    Logger logger = LoggerFactory.getLogger("Logger Rahul Tests");


    //Klasy stron
    protected LoginFormPage loginPageTest;
    ShopLoginPageForm pageShopLoginForm;
    ShopPage shopPage;
    CheckoutPage checkoutPage;

    @Parameters({"browser", "timeout"})
    @BeforeMethod(alwaysRun = true)
    public void config(@Optional("chrome") String browser, @Optional("25") int timeout, Method method) throws InterruptedException {
            // Inicjalizacja drivera (lokalnego lub zdalnego w zależności od potrzeb)
            DriverFactoryV1.initDriver(browser, timeout);
            logger.info("Rozpoczynam test: {}", method.getName());
            Class<?> testClass = method.getDeclaringClass();
            if (testClass.equals(LocatorsFormLoginTests.class)) {
                DriverFactoryV1.getDriver().get(formTestPage);
            } else if (testClass.equals(ShopPageTests.class)) {
                DriverFactoryV1.getDriver().get(shopLoginPage);
            } else {
                logger.error("Błędnie podany adres URL, test nie został uruchomiony.");
                throw new RuntimeException("Nie mogę uruchomić testu z powodu braku URL do strony testowej!!!");
            }
    }

    @AfterMethod(alwaysRun = true)
    public void testInfo(ITestResult testResult, Method method) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            logger.error("Test się nie powiódł!: {}", method.getName());
        } else if (ITestResult.SUCCESS == testResult.getStatus()) {
            logger.info("Test zakończony pomyślnie: {}", method.getName());
        }
    }

    @AfterMethod(alwaysRun = true)
    void shutDown() {
        logger.info("Testy zostały ukończone.");
        DriverFactoryV1.quit();
    }

    @AfterSuite
    public void cleanUpUserDataDirs() {
        // TODO: Usuń katalogi tymczasowe dla user-data-dir
    }
}








