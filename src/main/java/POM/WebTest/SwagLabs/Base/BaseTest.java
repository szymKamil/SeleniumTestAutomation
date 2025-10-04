package POM.WebTest.SwagLabs.Base;

import Base.BaseTest.DriverFactoryV1;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class BaseTest  {

    WebDriver driver;
   public Logger logger = LoggerFactory.getLogger("SwagLabs Test Logger");



    @BeforeClass
    void initiateDriver(@Optional("chrome") String browser, @Optional("25") int timeout) throws InterruptedException {
        DriverFactoryV1.initDriver(browser, timeout);
    }

    @BeforeMethod
    void startTest(){
            DriverFactoryV1.getDriver().get("https://www.saucedemo.com/");
    }

    @AfterClass
    void quitDriver(){
            DriverFactoryV1.getDriver().quit();
    }

}
