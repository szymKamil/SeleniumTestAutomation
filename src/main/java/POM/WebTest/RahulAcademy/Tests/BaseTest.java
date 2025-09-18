package POM.WebTest.RahulAcademy.Tests;


import Base.BaseTest.DriverFactoryV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URI;

public class BaseTest {


    protected final URI uri = null; /*= URI.create("http://192.168.1.108:4444")*/;
    private final String FORM_TEST_PAGE = "https://rahulshettyacademy.com/locatorspractice/";
    private final String SHOP_LOGIN_PAGE = "https://rahulshettyacademy.com/loginpagePractise/";
    private final String VEGETABLE_SHOP_PAGE = "https://rahulshettyacademy.com/seleniumPractise/#/";
    Logger logger = LoggerFactory.getLogger("Logger Rahul Tests");


    @Parameters({"browser", "timeout"})
    @BeforeMethod(alwaysRun = true)
    public void config(@Optional("chrome") String browser, @Optional("25") int timeout, Method method) throws InterruptedException {
            DriverFactoryV1.initDriver(browser, timeout);
            logger.info("Rozpoczynam test: {}", method.getName());
            Class<?> testClass = method.getDeclaringClass();
            if (testClass.equals(LocatorsFormLoginTests.class)) {
                DriverFactoryV1.getDriver().get(FORM_TEST_PAGE);
            } else if (testClass.equals(ShopPageTests.class)) {
                DriverFactoryV1.getDriver().get(SHOP_LOGIN_PAGE);
            } else if (testClass.equals(VegetableShopTests.class)) {
                DriverFactoryV1.getDriver().get(VEGETABLE_SHOP_PAGE);
            } else {
                logger.error("Błędnie podany adres URL, test nie został uruchomiony.");
                throw new RuntimeException("Nie mogę uruchomić testu z powodu braku URL do strony testowej!!!");
            }
    }

    @AfterMethod(alwaysRun = true)
    public void shutDown(ITestResult testResult, Method method) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            logger.error("Test się nie powiódł!: {}", method.getName());
        } else if (ITestResult.SUCCESS == testResult.getStatus()) {
            logger.info("Test zakończony pomyślnie: {}", method.getName());
        }
        logger.info("Testy zostały ukończone.");
        DriverFactoryV1.quit();
    }


    @AfterSuite
    public void cleanUpUserDataDirs() {
        // TODO: Usuń katalogi tymczasowe dla user-data-dir
    }
}








