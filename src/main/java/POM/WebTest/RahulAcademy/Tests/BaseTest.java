package POM.WebTest.RahulAcademy.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Listener.Listener;
import POM.WebTest.RahulAcademy.Pages.LoginTest.LoginPageTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;

public class BaseTest {


    protected  WebDriver driver;
    protected WebDriverWait wait;
    private DriverFactoryV1 factory;
    private Logger log;


    protected final URI uri = URI.create("http://192.168.1.108:4444");
    private final String pageURL = "https://rahulshettyacademy.com/locatorspractice/";

    //Klasy stron
    protected LoginPageTest loginPageTest;


    @Parameters({"browser", "timeout"})
    @BeforeClass()
    public void config(@Optional("Chrome") String browser, @Optional("25") int timeout) throws MalformedURLException {
        factory = new DriverFactoryV1(browser, timeout /*, uri.toURL()*/);
        this.driver = factory.getDriver();
        this.wait = factory.getWait();
        this.log = factory.getLogger();
        driver = new EventFiringDecorator<>(new Listener()).decorate(driver);
        loginPageTest = new LoginPageTest(driver, wait, log);

    }


    @BeforeMethod()
    public void config(Method method)  {
        log.info("Rozpoczynam test: {}", method.getName());
        Class<?> testClass = method.getDeclaringClass();
        if (testClass.equals(LocatorsFormLoginTests.class)) {
            driver.get(pageURL);
        }
    }

    @AfterMethod
    public void testInfo(ITestResult test, Method method){
        if (ITestResult.FAILURE == test.getStatus()){
            log.error("Test się nie powiódł!: {}", method.getName());
        } else if (ITestResult.SUCCESS == test.getStatus()) {
            log.info("Test zakończony pomyślnie: {}", method.getName());
        }
    }

    @AfterClass
    void shutDown(){
       log.info("Testy zostały ukończone.");
       if (driver != null) {
           driver.quit();
       }
    }







}
