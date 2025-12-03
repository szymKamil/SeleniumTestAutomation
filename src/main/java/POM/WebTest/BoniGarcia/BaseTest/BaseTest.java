package POM.WebTest.BoniGarcia.BaseTest;

import Base.Drivers.DriverFactory;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org. slf4j. Logger;
import java.lang.reflect.Method;
import java.net.URL;

public abstract class BaseTest {

	Logger log;
	@BeforeClass
	public void runLogger() {
		log = LoggerFactory.getLogger(BaseTest.class);
	}

	@Parameters({"browser", "timeout", "url"})
	@BeforeSuite
	public void config(@Optional("Chrome") String browser, @Optional("55") int timeout, @Optional("local") String url) throws Exception {
		if (url.equals("local")){
			DriverFactory.initDriver(browser, 30);
		} else {
			DriverFactory.initDriver(browser, 30, new URL(url));
		}
	}

	@BeforeMethod
	public void beforeTestInfo(Method method){
		log.info("Rozpoczynam test: {}, {}", method.getName(), method.getDeclaredAnnotations());
	}


    @AfterSuite(alwaysRun = true)
    public void shutDown() {
/*        if (ITestResult.FAILURE == testResult.getStatus()) {
            log.error("Test się nie powiódł!: {}", testResult.getName());
        } else if (ITestResult.SUCCESS == testResult.getStatus()) {
            log.info("Testy zakończone pomyślnie: {}", testResult.getName());
        }*/
        log.info("Testy zostały ukończone.");
        DriverFactory.quit();
    }

}
