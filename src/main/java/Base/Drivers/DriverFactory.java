package Base.Drivers;

import Base.Listeners.TestStepsListener;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
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

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.function.BiConsumer;

import static Base.Utils.FileDownloadUtils.getDownloadDirectory;

public final class DriverFactory {
	private static final ThreadLocal<WebDriver> DRIVER_THREAD = new ThreadLocal<>();
	private static final ThreadLocal<WebDriverWait> WAIT_THREAD = new ThreadLocal<>();
	public static Logger logger = LoggerFactory.getLogger(DriverFactory.class);

	//Na wszelki wypadek, by nikt nie tworzył konstruktora
	private DriverFactory() {
	}

	/**
	 * Mechanizm lokalnej inicjacji Drivera w teście. Posiada wersję 3 parametrową, z URL.
	 * @param browser  nazwa przeglądarki którą uruchamiamy w teście. Przyjmuje wartości: chrome, firefox, edge.
	 * @param time  parametr sterujący domyślnym czasem timeouta w teście.
	 */
	public static void initDriver(String browser, int time) {
		initDriver(browser, time, null);
	}

	/**
	 * Mechanizm inicjacji Drivera w teście. Domyślna metoda przyjmująca 3 parametry. Posiada także wersję 2 parametrową (bez URL).
	 * @param browser  nazwa przeglądarki którą uruchamiamy w teście. Przyjmuje wartości: chrome, firefox, edge.
	 * @param time  parametr sterujący domyślnym czasem timeouta w teście.
	 * @param url  opcjonalny parametr, pozwalający uruchomić testy zdalnie, na innej maszynie. Podajemy adres URL serwera.
	 */
	public static void initDriver(String browser, int time, URL url) {
		logger.info("Inicjalizacja drivera dla przeglądarki: {}, URL: {}", browser, url);
		WebDriverManager.getInstance(browser).setup();
		WebDriver driver = null;
		try {
			boolean urlUp = url != null && isURLup(url);
			if (url != null && urlUp) {
					System.setProperty("LocalTest", "false");
					driver = RemoteWebDriver.builder().oneOf(setBrowserSettings(browser)).address(url).build();
					logger.info("Uruchamiam testy zdalnie, środowisko zdalne ma status: {}", urlUp);
			} else {
                System.setProperty("LocalTest", "true");
				logger.info("Uruchamiam testy lokalnie");
				switch (browser.toLowerCase()) {
					case "chrome" -> driver = new ChromeDriver((ChromeOptions) setBrowserSettings(browser));
					case "firefox" -> driver = new FirefoxDriver((FirefoxOptions) setBrowserSettings(browser));
					case "edge" -> driver = new EdgeDriver((EdgeOptions) setBrowserSettings(browser));
					default -> throw new IllegalArgumentException("Błędnie wybrana przeglądarka!!! Dozwolone wartości to chrome, firefox i edge. Wpisałeś: " + browser);
				}
			}
			var decoratedDriver = Objects.requireNonNull(decorate(driver, new TestStepsListener()), "Udekorowany driver jest null, wystąpił błąd!");
			Augmenter augmenter = new Augmenter();
			var augmentedDriver = Objects.requireNonNull(augmenter.augment(decoratedDriver));
			try {
				augmentedDriver.manage()
						.window()
						.maximize();
			} catch (WebDriverException e) {
				logger.warn("Nie udało się zmaksymalizować okna: {}", e.getMessage());
			}
			DRIVER_THREAD.set(augmentedDriver);
			WAIT_THREAD.set(new WebDriverWait(augmentedDriver, Duration.ofSeconds(time)));
			logger.info("Driver uruchomiony pomyślnie dla wątku: {}", Thread.currentThread().getId());
		} catch (WebDriverException | IOException | NoSuchFieldException e) {
			logger.error("Błąd podczas inicjalizacji drivera: {}", e.getMessage());
			throw new WebDriverException("Nie udało się uruchomić drivera", e.getCause());
		}
		logger.info("Test został uruchomiony w wątku {}", Thread.currentThread().getName());
	}

	/**
	 * Metoda dekorująca drivera klasami nasłuchującymi.
	 * @param rawDriver "czysty" driver który ma zostać "udekorowany".
	 * @param listeners nasłuchiwacze, które mają zostać dodane. Dodajemy je kolejno po przecinku.
	 * @return zwracany jest udekorowany WebDriver.
	 */
	private static WebDriver decorate(WebDriver rawDriver, WebDriverListener... listeners) {
		WebDriver decorated = rawDriver;
		for (WebDriverListener listener : listeners) {
			decorated = new EventFiringDecorator<>(listener).decorate(decorated);
		}
		return Objects.requireNonNull(decorated, "Wystąpił problem z dekorowaniem drivera.");
	}

	private static AbstractDriverOptions<?> setBrowserSettings(String browser) throws NoSuchFieldException {
		AbstractDriverOptions<?> options;
		switch (browser.toLowerCase()) {
			case "chrome" -> options = new ChromeOptions();
			case "firefox" -> options = new FirefoxOptions();
			case "edge" -> options = new EdgeOptions();
			default ->
					throw new IllegalArgumentException("Błędna nazwa przeglądarki ->'%s'. Użyj jednej z następujących: chrome, firefox, edge.".formatted(browser));
		}
		options.setAcceptInsecureCerts(true).setPageLoadStrategy(PageLoadStrategy.NORMAL).setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
		Path argumentsPath = getSettingsFilePath(options, "arg");
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(argumentsPath.toFile()))) {
			String argumentLine;
			List<String> argumentList = new ArrayList<>();
			while ((argumentLine = bufferedReader.readLine()) != null) {
				String[] optionSplit = argumentLine.split(">");
				if (optionSplit.length == 2) {
					String key = optionSplit[0].trim();
					String value = optionSplit[1].trim();
					if (!value.equals("false") && !key.contains("local")) {
						argumentList.add(key);
					}
				}
			}
			addPreferencesToDriver(options, argumentList);
		} catch (IOException e) {
			System.out.println("Błąd podczas wczytywania danych z pliku: " + e);
		}
		logger.info("Uruchamiam testy z następującymi ustawieniami: " + options.asMap());
		return options;
	}

	private static Path getSettingsFilePath(AbstractDriverOptions<?> options, String arg) throws NoSuchFieldException {
		logger.info("Uruchamiam pobranie pliku");
		if ((options instanceof ChromeOptions || options instanceof EdgeOptions) && arg.contains("arg")){
			return Path.of("src/main/resources/argumentsChromeEdge.properties");
		} else if ((options instanceof ChromeOptions || options instanceof EdgeOptions) && arg.contains("pref")){
			return Path.of("src/main/resources/chromiumPrefs.properties");
		} else if (options instanceof FirefoxOptions && arg.contains("arg")) {
			return Path.of("src/main/resources/argumentsFirefox.properties");
		} else if (options instanceof FirefoxOptions && arg.contains("pref")) {
			return Path.of("src/main/resources/firefoxPrefs.properties");
		}
		else {
			throw new NoSuchFieldException();
		}
	}

	private static void addPreferencesToDriver(AbstractDriverOptions<?> options, List<String> argumentsList) throws IOException, NoSuchFieldException {
		if (options instanceof ChromeOptions chromeOptions) {
			 chromeOptions.addArguments(argumentsList);
			 var pref = getBrowserPreferences(options);
			if (!pref.isEmpty()) {
				chromeOptions.setExperimentalOption("prefs", pref);
			}
		} else if (options instanceof EdgeOptions edgeOptions) {
			edgeOptions.addArguments(argumentsList);
			var pref = getBrowserPreferences(options);
			if (!pref.isEmpty()) {
				edgeOptions.setExperimentalOption("prefs", pref);
			}
		} else if (options instanceof FirefoxOptions firefoxOptions) {
			firefoxOptions.addArguments(argumentsList);
			firefoxOptions.enableBiDi();
			iterateMap(getBrowserPreferences(options), firefoxOptions::addPreference);

		}
	}

	static Map<String, Object> getBrowserPreferences(AbstractDriverOptions<?> options) throws IOException, NoSuchFieldException {
		Map<String, Object> prefs = new HashMap<>();
		Path prefPath = getSettingsFilePath(options, "pref");
		logger.info("Ścieżka do pliku z preferencjami to {}", prefPath);
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(prefPath.toFile()));
			logger.info("Buffered reader: {}", bufferedReader);
			String prefLine;
			while ((prefLine = bufferedReader.readLine()) != null) {
				if(prefLine.contains("${DownloadPath}")){
					prefLine = prefLine.replace("${DownloadPath}", getDownloadDirectory().toString());
				}
				String[] optionSplit = prefLine.split("=", 2);
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

	static void iterateMap(Map<String, Object> map, BiConsumer<String, Object> consumer){
		for (Map.Entry<String, Object> entry : map.entrySet()){
			consumer.accept(entry.getKey(), entry.getValue());
		}
	}

	public static WebDriver getDriver() {
		WebDriver driver = DRIVER_THREAD.get();
		if (driver == null) {
			throw new IllegalStateException("Driver nie został zainicjowany dla wątku: " + Thread.currentThread()
					.threadId());
		}
		return driver;
	}

	public static WebDriverWait getWait() {
		WebDriverWait wait = WAIT_THREAD.get();
		if (wait == null) {
			throw new IllegalStateException("WebDriverWait nie został zainicjowany dla wątku: " + Thread.currentThread().threadId());
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
