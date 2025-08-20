package Base.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactoryV1 {
    // Tworzenie wielowątkowości na potrzeby izolacji testów przy parallelnym uruchomieniu.
    private static final ThreadLocal<WebDriver> DRIVER_THREAD = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> WAIT_THREAD = new ThreadLocal<>();
    private static final ThreadLocal<Logger> THREAD_LOCAL = new ThreadLocal<>();

    private DriverFactoryV1() {
    }


    public static void initDriver(String browser, int time) throws InterruptedException {
        initDriver(browser, time, null);
    }

    public static void initDriver(String browser, int time, URL url) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(DriverFactoryV1.class);
        logger.info("Inicjalizacja drivera dla przeglądarki: {}, URL: {}", browser, url);
        WebDriverManager.getInstance(browser).setup();
        Thread.sleep(Duration.ofSeconds(3));
        WebDriver driver = null;
        try {
        if (url != null) {
//             driver = RemoteWebDriver.builder().oneOf(loadOptionsFromFile(browser)).address(url).build();
            driver = new RemoteWebDriver(url,loadOptionsFromFile(browser, true));
        } else {
            switch(browser.toLowerCase()) {
                case "chrome" -> driver = new ChromeDriver((ChromeOptions) loadOptionsFromFile(browser, false));
                case "firefox" -> driver = new FirefoxDriver((FirefoxOptions) loadOptionsFromFile(browser, false));
                case "edge" -> driver = new EdgeDriver((EdgeOptions) loadOptionsFromFile(browser, false));
                default -> logger.info("Błędnie wybrana przeglądarka!!!");
            }
        }
        DRIVER_THREAD.set(driver);
        WAIT_THREAD.set(new WebDriverWait(driver, Duration.ofSeconds(time)));
        THREAD_LOCAL.set(LoggerFactory.getLogger(DriverFactoryV1.class));
        logger.info("Driver uruchomiony pomyślnie dla wątku: {}", Thread.currentThread().getId());
        } catch (Exception e) {
            logger.error("Błąd podczas inicjalizacji drivera: ", e);
            throw new RuntimeException("Nie udało się uruchomić drivera", e);
        }
    }


     static Capabilities loadOptionsFromFile(String browser, boolean flag)  {
        AbstractDriverOptions<?> options;
        switch(browser.toLowerCase()) {
            case "chrome" -> options = new ChromeOptions();
            case "firefox" -> options = new FirefoxOptions();
            case "edge" -> options = new EdgeOptions();
            default -> throw new IllegalArgumentException("Błędna nazwa przeglądarki %s. Użyj jednej z następujących: chrome, firefox, edge.".formatted(browser));
        }
        Path optionPath = Path.of("src/main/resources/options.properties");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(optionPath.toFile()))){
            String optionsLine;
            while((optionsLine = bufferedReader.readLine()) != null){
            String[] optionSplit = optionsLine.split(">");
                if (optionSplit.length == 2){
                    String key = optionSplit[0].trim();
                    String value = optionSplit[1].trim();
                    if (!flag == true && !key.contains("local")){
                        addOptionsToDriver(options, key, value);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Błąd podczas wczytywania danych z pliku: " + e);
        }
        System.out.println("Opcje zostały poprawnie dodane do drivera");

        return options;
    }

    private static void addOptionsToDriver(AbstractDriverOptions<?> options, String key, String value){
        if (options instanceof ChromeOptions chromeOptions) {
            if (value.equalsIgnoreCase("true")) {
                chromeOptions.addArguments("--" + key);
            } else if (value.contains(",")) {
                chromeOptions.addArguments("--" + key + "=" + value);

            } else if (key.equals("user-data-dir")) {
                String threadId = String.valueOf(Thread.currentThread().getId());
                chromeOptions.addArguments("--" + key + "=" + value.replace("${threadId}", threadId));
            }
            Map<String, Object> prefs = addExperimentalOptions();
            if (!prefs.isEmpty()) {
                chromeOptions.setExperimentalOption("prefs", prefs);
            }
            System.out.println("Dodałem opcje eksperymentalne");

        }
        if (options instanceof FirefoxOptions firefoxOptions){
            if (value.equalsIgnoreCase("true")){
                firefoxOptions.addArguments("--" + key);
            } else if (value.contains(",")){
                firefoxOptions.addArguments("--" + key + "=" +value);
            }
        }
        if (options instanceof EdgeOptions edgeOptions){
            if (value.equalsIgnoreCase("true")){
                edgeOptions.addArguments("--" + key);
            } else if (value.contains(",")){
                edgeOptions.addArguments("--" + key + "=" +value);
            }
        }
    }

     static Map<String, Object> addExperimentalOptions(){
        Map<String, Object> prefs = new HashMap<>();
        Path path = Path.of("src/main/resources/experimentalOptions.properties");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
            String optionLine;
            while ((optionLine = bufferedReader.readLine()) != null){
               String[] optionSplit = optionLine.split("=");
               if(optionSplit[1] != null){
                   try {
                       prefs.put(optionSplit[0].trim(), Integer.valueOf(optionSplit[1].trim()));

                   } catch (NumberFormatException e){
                       prefs.put(optionSplit[0].trim(), optionSplit[1].trim());

                   }
               }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         System.out.println("Dodane zostaną następujące ustawienia eksperymentalne: " + prefs);
        return prefs;
    }


    public static Logger getLogger() {
        return THREAD_LOCAL.get();
    }

    public static WebDriver getDriver() {
        return DRIVER_THREAD.get();
    }

    public static WebDriverWait getWait() {
        return WAIT_THREAD.get();
    }


    public static void quit() {
        WebDriver driver = DRIVER_THREAD.get();
        if (driver != null) {
            driver.quit();
            DRIVER_THREAD.remove();
            WAIT_THREAD.remove();
            THREAD_LOCAL.remove();
        }
    }
}
