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
	@BeforeMethod
	public void config(@Optional("Chrome") String browser, @Optional("55") int timeout, @Optional("http://localhost:4444/") String url) throws Exception {
		if (url.equals("local")){
			DriverFactory.initDriver(browser, 30);
		} else {
			DriverFactory.initDriver(browser, 30, new URL(url));
		}
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
