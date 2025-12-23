package BiDi;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.bidi.log.LogEntry;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.network.AddInterceptParameters;
import org.openqa.selenium.bidi.network.InterceptPhase;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BiDiLogs {

	FirefoxDriver driver;

	@BeforeMethod
	public void setup() {
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("webSocketUrl", true);
		driver = new FirefoxDriver(options);
	}

	@Test
	void canAddConsoleMessageHandler()
			throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
		CompletableFuture<ConsoleLogEntry> future = new CompletableFuture<>();

		long id = ((RemoteWebDriver) driver).script().addConsoleMessageHandler(future::complete);

		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
		driver.findElement(By.id("consoleLog")).click();

		ConsoleLogEntry logEntry = future.get(5, TimeUnit.SECONDS);

		Assert.assertEquals("Hello, world!", logEntry.getText());

		((RemoteWebDriver) driver).script().removeConsoleMessageHandler(id);
	}

	@Test
	void canFailRequest() {
		try (Network network = new Network(driver)) {
			network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT));
			network.onBeforeRequestSent(
					responseDetails -> network.failRequest(responseDetails.getRequest().getRequestId()));
			driver.manage().timeouts().pageLoadTimeout(Duration.of(5, ChronoUnit.SECONDS));
			Assert.assertThrows(TimeoutException.class, () -> driver.get("https://the-internet.herokuapp.com/basic_auth"));
		}
	}

	@Test
	void canAddConsoleMessageHandlerMoje()
			throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
		CompletableFuture<ConsoleLogEntry> future = new CompletableFuture<>();
		BiDi biDi = null;

		if (driver instanceof HasBiDi hasBiDi){
			biDi = hasBiDi.getBiDi();
		}
		CompletableFuture<LogEntry> logFuture = new CompletableFuture<>();

		biDi.addListener(Log.entryAdded(), (LogEntry logEntry) -> {
			// Tutaj możesz dodać warunek, np. szukając konkretnego tekstu,
			// poziomu logowania (np. ERROR) lub typu (Console/JS).
			if (logEntry.getConsoleLogEntry().isPresent()) {
				// Log został znaleziony - Zakończ oczekiwanie
				logFuture.complete(logEntry);
			}
		});

		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
		driver.findElement(By.id("consoleLog")).click();

		try {
			LogEntry znalezionyLog = logFuture.get(15, TimeUnit.SECONDS);
			System.out.println("✅ Log ZNALEZIONY: " + znalezionyLog.getConsoleLogEntry().get().getText());
		} catch (Exception e) {
			System.err.println("❌ Timeout lub błąd: Nie znaleziono oczekiwanego logu w ciągu 5 sekund.");
		}
		driver.quit();

	}

	@Test
	void canRemoveConsoleMessageHandler() {
		CopyOnWriteArrayList<ConsoleLogEntry> logs = new CopyOnWriteArrayList<>();

		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

		long id = ((RemoteWebDriver) driver).script().addConsoleMessageHandler(logs::add);
		((RemoteWebDriver) driver).script().removeConsoleMessageHandler(id);

		driver.findElement(By.id("consoleLog")).click();

		Assert.assertEquals(0, logs.size());
	}

	@Test
	void canAddJsErrorHandler() throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
		CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<>();

		long id = ((RemoteWebDriver) driver).script().addJavaScriptErrorHandler(future::complete);

		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
		driver.findElement(By.id("jsException")).click();

		JavascriptLogEntry logEntry = future.get(5, TimeUnit.SECONDS);

		Assert.assertEquals("Error: Not working", logEntry.getText());
		System.out.println(logEntry.getText());

		((RemoteWebDriver) driver).script().removeJavaScriptErrorHandler(id);
	}

	@Test
	void canRemoveJsErrorHandler() {
		CopyOnWriteArrayList<JavascriptLogEntry> logs = new CopyOnWriteArrayList<>();

		long id = ((RemoteWebDriver) driver).script().addJavaScriptErrorHandler(logs::add);
		((RemoteWebDriver) driver).script().removeJavaScriptErrorHandler(id);

		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
		driver.findElement(By.id("jsException")).click();

		Assert.assertEquals(0, logs.size());
	}
}

