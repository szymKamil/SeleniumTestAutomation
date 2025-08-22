package POM.WebTest.BoniGarcia.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org. slf4j. Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class BaseTest  {
    /*------Bazowe-----------*/
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;

    /*-----------------*/
    final static URI LOCAL_URL =  URI.create("http://localhost:4444/");
    final static URI DOCKER_URL = URI.create("http://192.168.1.104:5555/");
    /*-----------------*/

    /*------Klasy stron-----------*/
    AbstractPage ap;
    /*-----------------*/



    //    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("55") int timeout) throws Exception {
        DriverFactoryV1.initDriver(browser, timeout/*, LOCAL_URL.toURL()*/);
        this.driver = DriverFactoryV1.getDriver();
        this.wait = DriverFactoryV1.getWait();
        this.log = DriverFactoryV1.getLogger();
        ap = new AbstractPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait(), DriverFactoryV1.getLogger());
    }


    @AfterMethod
    void tearDown(ITestResult result){
        log.info("Test {} zakończony.", result.getMethod().getMethodName());
        DriverFactoryV1.quit();
    }
}
