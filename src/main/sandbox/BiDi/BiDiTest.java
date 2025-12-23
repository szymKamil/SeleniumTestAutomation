package BiDi;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.bidi.network.AddInterceptParameters;
import org.openqa.selenium.bidi.network.ContinueRequestParameters;
import org.openqa.selenium.bidi.network.InterceptPhase;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Event;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.runtime.model.ExceptionThrown;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.Bidi;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.devtools.events.CdpEventTypes.consoleEvent;

public class BiDiTest {


	@Test
	public void consoleLogs() {
		FirefoxOptions options = new FirefoxOptions();
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
		CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();

		((HasLogEvents) driver).onLogEvent(consoleEvent(e -> messages.add(e.getMessages()
				.get(0))));

		driver.findElement(By.id("consoleLog"))
				.click();
		driver.findElement(By.id("consoleError"))
				.click();

		wait.until(_d -> messages.size() > 1);
		Assert.assertTrue(messages.contains("Hello, world!"));
		Assert.assertTrue(messages.contains("I am console error"));
		for (String msg : messages) {
			System.out.println(msg);
		}
		driver.quit();
	}


}










