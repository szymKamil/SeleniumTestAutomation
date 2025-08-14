package POM.WebTest.RahulAcademy.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.LoginPageTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
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
        loginPageTest = new LoginPageTest(driver, wait, log);


    }

    @BeforeMethod()
    public void config(Method method)  {
        Class<?> testClass = method.getDeclaringClass();
        if (testClass.equals(LocatorsFormLoginTests.class)) {
            driver.get(pageURL);
        }
    }


    @AfterClass
    void shutDown(){
       log.info("Test ukończony.");
       if (driver != null) {
           factory.quit();
       }
    }







}
