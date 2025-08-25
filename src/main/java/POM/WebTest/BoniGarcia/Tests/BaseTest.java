package POM.WebTest.BoniGarcia.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org. slf4j. Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class BaseTest  {


    /*-----------------*/
    final static URI LOCAL_URL =  URI.create("http://localhost:4444/");
    final static URI DOCKER_URL = URI.create("http://192.168.1.104:5555/");
    /*-----------------*/
    Logger log;
    /*------Klasy stron-----------*/
    AbstractPage ap;
    /*-----------------*/


    //    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("55") int timeout) throws Exception {
        DriverFactoryV1.initDriver(browser, timeout/*, LOCAL_URL.toURL()*/);
        log = LoggerFactory.getLogger("Logger");
        ap = new AbstractPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
    }


     @AfterMethod
        void tearDown(ITestResult result){
        log.info("Test {} zakończony.", result.getMethod().getMethodName());
        DriverFactoryV1.quit();
    }
}
