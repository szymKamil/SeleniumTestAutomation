package POM.WebTest.BoniGarcia.BaseTest;

import Base.Drivers.DriverFactory;
import io.qameta.allure.Story;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org. slf4j. Logger;
import java.lang.reflect.Method;
import java.net.URL;

public abstract class BaseTest {

	Logger log = LoggerFactory.getLogger(BaseTest.class);;

	@Parameters({"browser", "timeout", "url"})
	@BeforeMethod(alwaysRun = true)
	public void config(@Optional("Chrome") String browser, @Optional("55") int timeout, @Optional("local") String url, Method method) throws Exception {
		if (url.equals("local")){
			log.info("Uruchamiam test lokalnie, na przeglądarce: {}, z timeoutem: {}", browser, timeout);
			DriverFactory.initDriver(browser, 30);
		} else {
			log.info("Uruchamiam test zdalnie, na przeglądarce: {}, z timeoutem: {}, łączę się z URL: {}", browser, timeout, url);
			DriverFactory.initDriver(browser, 30, new URL(url));
		}
		Story story = method.getAnnotation(Story.class);
		log.info("Rozpoczynam test: {}, {}", method.getName(), story != null ? story : "Brak adnotacji @Story dla testu.");
	}


    @AfterMethod(alwaysRun = true)
    public void shutDown() {
        log.info("Testy zostały ukończone.");
        DriverFactory.quit();
    }

}
