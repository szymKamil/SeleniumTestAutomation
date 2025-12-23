package Base.Drivers;

import Base.Listeners.TestStepsListener;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.events.WebDriverListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Base.Utils.FileDownloadUtils.getDownloadDirectory;

public final class DriverFactory {
	private static final ThreadLocal<WebDriver> DRIVER_THREAD = new ThreadLocal<>();
	private static final ThreadLocal<WebDriverWait> WAIT_THREAD = new ThreadLocal<>();
	public static Logger logger = LoggerFactory.getLogger(DriverFactory.class);

	private DriverFactory() {
	}

	public static void initDriver(String browser, int time) {
		initDriver(browser, time, null);
	}

	public static void initDriver(String browser, int time, URL url) {
		logger.info("Inicjalizacja drivera dla przeglądarki: {}, URL: {}", browser, url);
		WebDriverManager.getInstance(browser).setup();
		WebDriver driver = null;
		try {
			if (url != null && !url.getPath().equals("local") && isURLup(url)) {
					System.setProperty("LocalTest", "false");
					driver = RemoteWebDriver.builder().oneOf(loadOptionsFromFile(browser)).address(url).build();
				// driver = new RemoteWebDriver(url, loadOptionsFromFile(browser, true));
					logger.info("Uruchamiam testy zdalnie, środowisko zdalne ma status: {}", isURLup(url));
			} else {
                System.setProperty("LocalTest", "true");
				switch (browser.toLowerCase()) {
					case "chrome" -> driver = new ChromeDriver((ChromeOptions) loadOptionsFromFile(browser));
					case "firefox" -> driver = new FirefoxDriver((FirefoxOptions) loadOptionsFromFile(browser));
					case "edge" -> driver = new EdgeDriver((EdgeOptions) loadOptionsFromFile(browser));
					default -> logger.info("Błędnie wybrana przeglądarka!!!");
				}
			}
			var decoratedDriver = decorate(driver, new TestStepsListener());
			decoratedDriver.manage()
					.window()
					.maximize();
			DRIVER_THREAD.set(decoratedDriver);
			WAIT_THREAD.set(new WebDriverWait(driver, Duration.ofSeconds(time)));
			logger.info("Driver uruchomiony pomyślnie dla wątku: {}", Thread.currentThread()
					.getId());
		} catch (WebDriverException | IOException e) {
			logger.error("Błąd podczas inicjalizacji drivera: ", e);
			quit();
			throw new WebDriverException("Nie udało się uruchomić drivera", e);
		}
		logger.info("Uruchomiony został thread {}", Thread.currentThread()
				.getName());
	}

	private static WebDriver decorate(WebDriver rawDriver, WebDriverListener... listeners) {
		WebDriver decorated = rawDriver;
		for (WebDriverListener listener : listeners) {
			decorated = new EventFiringDecorator(listener).decorate(decorated);
		}
		return decorated;
	}


	static AbstractDriverOptions<?> loadOptionsFromFile(String browser) {
		AbstractDriverOptions<?> options;
		switch (browser.toLowerCase()) {
			case "chrome" -> options = new ChromeOptions();
			case "firefox" -> options = new FirefoxOptions();
			case "edge" -> options = new EdgeOptions();
			default ->
					throw new IllegalArgumentException("Błędna nazwa przeglądarki ->'%s'. Użyj jednej z następujących: chrome, firefox, edge.".formatted(browser));
		}
		Path optionPath = Path.of("src/main/resources/options.properties");
		Path firefoxOptionPath = Path.of("src/main/resources/firefoxOptions.properties");

		List<Path> paths = new ArrayList<>(List.of(optionPath, firefoxOptionPath));
		boolean firefoxPropertiesFlag;

		for (Path path : paths) {
				firefoxPropertiesFlag = path.getFileName().toString().equals("firefoxOptions.properties");
				try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
					String optionsLine;
					while ((optionsLine = bufferedReader.readLine()) != null) {
						String[] optionSplit = optionsLine.split(">");
						if (optionSplit.length == 2) {
							String key = optionSplit[0].trim();
							String value = optionSplit[1].trim();
							if (/*!value.equals("false") &&*/ !key.contains("local")) {
								addOptionsToDriver(options, key, value, firefoxPropertiesFlag);
							}
						}
					}
				} catch (IOException e) {
					System.out.println("Błąd podczas wczytywania danych z pliku: " + e);
				}
		}
		logger.info("Uruchamiam testy z następującymi opcjami: " + options.asMap());
		return options;
	}


	private static void addOptionsToDriver(AbstractDriverOptions<?> options, String key, String value, boolean firefoxPropertiesFlag) throws IOException {
		if (options instanceof ChromeOptions chromeOptions && !firefoxPropertiesFlag) {
			if (value.contains("true")) {
				chromeOptions.addArguments("--" + key);
			} else if (value.contains(",")) {
				chromeOptions.addArguments("--" + key + "=" + value);
			} else if (key.equals("user-data-dir")) {
				String threadId = String.valueOf(Thread.currentThread()
						.getId());
				chromeOptions.addArguments("--" + key + "=" + value.replace("${threadId}", threadId));
			}
            Map<String, Object> prefs = addExperimentalOptions();
			if (!prefs.isEmpty()) {
				chromeOptions.setExperimentalOption("prefs", prefs);
			}
		}
		//TODO: zrobić jakiś bardziej elegancką weryfikację plików z firefoxOptions
		if (options instanceof FirefoxOptions firefoxOptions) {
			if (value.contains("true") && !firefoxPropertiesFlag) {
				firefoxOptions.addArguments("--" + key);
			} else if (value.contains(",") && !firefoxPropertiesFlag) {
				firefoxOptions.addArguments("--" + key + "=" + value);
			} else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
				firefoxOptions.addPreference(key, Boolean.parseBoolean(value));
			} else if (firefoxPropertiesFlag) {
				if (value.contains("${DownloadPath}")){
					value = value.replace("${DownloadPath}", getDownloadDirectory().toString());
					firefoxOptions.addPreference(key, value);
				} else if (Character.isDigit(value.charAt(0))){
					int intValue = Integer.parseInt(value);
					firefoxOptions.addPreference(key, intValue);
				} else {
					firefoxOptions.addPreference(key, value);
				}
			}
			firefoxOptions.enableBiDi();
			firefoxOptions.setCapability("se:downloadsEnabled", true);
			//firefoxOptions.addPreference("media.navigator.streams.fake", true);
		}
		if (options instanceof EdgeOptions edgeOptions && !firefoxPropertiesFlag) {
			if (value.contains("true")) {
				edgeOptions.addArguments("--" + key);
			} else if (value.contains(",")) {
				edgeOptions.addArguments("--" + key + "=" + value);
			}
			Map<String, Object> prefs = addExperimentalOptions();
			if (!prefs.isEmpty()) {
				edgeOptions.setExperimentalOption("prefs", prefs);
			}
			//edgeOptions.setCapability("webSocketUrl", true);
			edgeOptions.setCapability("se:downloadsEnabled", true);

		}
	}

	static Map<String, Object> addExperimentalOptions() throws IOException {
		Map<String, Object> prefs = new HashMap<>();
		Path expPropFile = java.nio.file.Path.of("src/main/resources/experimentalOptions.properties");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(expPropFile.toFile()));
			String optionLine;
			while ((optionLine = bufferedReader.readLine()) != null) {
				if(optionLine.contains("${DownloadPath}")){
					optionLine = optionLine.replace("${DownloadPath}", getDownloadDirectory().toString());
				}
				String[] optionSplit = optionLine.split("=");
				if (optionSplit[1] != null) {
					try {
						prefs.put(optionSplit[0].trim(), Integer.valueOf(optionSplit[1].trim()));
					} catch (NumberFormatException e) {
						prefs.put(optionSplit[0].trim(), optionSplit[1].trim());
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
		}
		return prefs;
	}

	public static WebDriver getDriver() {
		WebDriver driver = DRIVER_THREAD.get();
		if (driver == null) {
			throw new IllegalStateException("Driver nie został zainicjalizowany dla wątku: " + Thread.currentThread()
					.threadId());
		}
		return driver;
	}

	public static WebDriverWait getWait() {
		WebDriverWait wait = WAIT_THREAD.get();
		if (wait == null) {
			throw new IllegalStateException("WebDriverWait nie został zainicjalizowany dla wątku: " + Thread.currentThread().threadId());
		}
		return wait;
	}

	public static Logger getLogger() {
		if (logger != null) {
			return logger;
		} else {
			throw new RuntimeException("Problem z uruchomieniem loggera");
		}
	}

	public static void quit() {
		WebDriver driver = DRIVER_THREAD.get();
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				logger.error("Błąd podczas zamykania drivera: ", e);
			} finally {
				DRIVER_THREAD.remove();
				WAIT_THREAD.remove();
			}
		}
	}

	public static Boolean isURLup(URL url) throws IOException {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			System.out.println("Status połączenia to: " + responseCode);
			return (200 <= responseCode && responseCode <= 399);
		} catch (Exception e) {
			return false;
		}

	}

}
