package POM.WebTest.SwagLabs.Base;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class BaseTest  {

    WebDriver driver;
   public Logger logger = LoggerFactory.getLogger("SwagLabs Test Logger");



    @BeforeClass
    void initiateDriver(@Optional("chrome") String browser, @Optional("25") int timeout) throws InterruptedException {
        DriverFactory.initDriver(browser, timeout);
    }

    @BeforeMethod
    void startTest(){
            DriverFactory.getDriver().get("https://www.saucedemo.com/");
    }

    @AfterClass
    void quitDriver(){
            DriverFactory.getDriver().quit();
    }

}
