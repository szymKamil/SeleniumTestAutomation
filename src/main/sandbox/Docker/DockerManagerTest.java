package Docker;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.annotations.Test;


import static io.github.bonigarcia.wdm.WebDriverManager.isDockerAvailable;
import static org.assertj.core.api.Assertions.assertThat;


public class DockerManagerTest {


		WebDriver driver;

		WebDriverManager wdm = WebDriverManager.chromedriver()
				.browserInDocker().browserInDocker()
				.browserVersion("beta");

		@BeforeClass
		public void setupTest() {
			if (!isDockerAvailable()) {
				throw new SkipException("Docker is not available");
			}
			driver = wdm.create();
		}

		@AfterClass
		public void teardown() {
			wdm.quit();
		}

		@Test
		public void testDockerChromeBeta() {
			driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
			assertThat(driver.getTitle()).contains("Selenium WebDriver");
		}


}


