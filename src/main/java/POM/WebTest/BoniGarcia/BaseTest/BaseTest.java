package POM.WebTest.BoniGarcia.BaseTest;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.*;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org. slf4j. Logger;

import java.net.URI;
import java.net.URL;

public abstract class BaseTest  {


    /*-----------------*/
    final static URI LOCAL_URL =  URI.create("http://localhost:4444/");
    final static URI DOCKER_URL = URI.create("http://192.168.1.105:5555/");
    /*-----------------*/
    Logger log;
    /*------Klasy stron-----------*/
    AbstractPage ap;
    /*-----------------*/


    //    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout", "url"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("55") int timeout,  @Optional("http://localhost:4444/wd/hub") String url) throws Exception {
        DriverFactoryV1.initDriver(browser, timeout, new URL(url));
        log = LoggerFactory.getLogger("Logger");
        ap = new AbstractPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
    }


     @AfterMethod
        void tearDown(ITestResult result){
        log.info("Test {} zakończony.", result.getMethod().getMethodName());
        DriverFactoryV1.quit();
    }
}
