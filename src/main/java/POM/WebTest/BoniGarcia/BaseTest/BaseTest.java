package POM.WebTest.BoniGarcia.BaseTest;


import Base.Drivers.DriverFactory;
import POM.WebTest.BoniGarcia.Pages.*;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org. slf4j. Logger;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

public abstract class BaseTest {


	/*-----------------*/
	public static final String LOCAL_URL = "http://localhost:4444/wd/hub";
	static final URI DOCKER_URL = URI.create("http://192.168.1.105:5555/");
	/*-----------------*/
	Logger log;
	/*------Klasy stron-----------*/
	AbstractPage ap;
	/*-----------------*/


	@BeforeClass
	public void runLogger() {
		log = LoggerFactory.getLogger(BaseTest.class);
	}


	//    @Listeners(TestListener.class)
	@Parameters({"browser", "timeout", "url"})
	@BeforeMethod
	public void config(@Optional("Chrome") String browser, @Optional("55") int timeout, @Optional(LOCAL_URL) String url) throws Exception {
		DriverFactory.initDriver(browser, 30/*, new URL(url)*/);
	}


    @AfterMethod(alwaysRun = true)
    public void shutDown(ITestResult testResult, Method method) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            log.error("Test się nie powiódł!: {}", method.getName());
        } else if (ITestResult.SUCCESS == testResult.getStatus()) {
            log.info("Test zakończony pomyślnie: {}", method.getName());
        }
        log.info("Testy zostały ukończone.");
        DriverFactory.quit();
    }

}
