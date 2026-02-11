package POM.WebTest.RahulAcademy.Pages.BaseTest;


import Base.Drivers.DriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Optional;


import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {


    private final String FORM_TEST_PAGE = "https://rahulshettyacademy.com/locatorspractice/";
    private final String SHOP_LOGIN_PAGE = "https://rahulshettyacademy.com/loginpagePractise/";
    private final String VEGETABLE_SHOP_PAGE = "https://rahulshettyacademy.com/seleniumPractise/#/";
    public Logger logger = LoggerFactory.getLogger("Logger Rahul Tests");


    @Parameters({"browser", "timeout", "url"})
    @BeforeMethod(alwaysRun = true)
    public void config(@Optional("chrome") String browser, @Optional("55") int timeout, @Optional("local") String url, Method method) throws URISyntaxException, MalformedURLException {
        if (url.equals("local")){
            logger.info("Uruchamiam test lokalnie, na przeglądarce: {}, z timeoutem: {}", browser, timeout);
            DriverFactory.initDriver(browser, 30);
        } else {
            logger.info("Uruchamiam test zdalnie, na przeglądarce: {}, z timeoutem: {}, łączę się z URL: {}", browser, timeout, url);
            DriverFactory.initDriver(browser, 30, new URI(url).toURL());
        }
        logger.info("Rozpoczynam test: {}", method.getName());
        Class<?> testClass = method.getDeclaringClass();
        if (testClass.getName().contains("LocatorsFormLoginTests")) {
            DriverFactory.getDriver().get(FORM_TEST_PAGE);
        } else if (testClass.getName().contains("ShopPageTests")) {
            DriverFactory.getDriver().get(SHOP_LOGIN_PAGE);
        } else if (testClass.getName().contains("VegetableShopTests")) {
            DriverFactory.getDriver().get(VEGETABLE_SHOP_PAGE);
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
        DriverFactory.quit();
    }


    @AfterSuite
    public void cleanUpUserDataDirs() {
        // TODO: Usuń katalogi tymczasowe dla user-data-dir
    }
}








