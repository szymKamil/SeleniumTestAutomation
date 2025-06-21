import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class WebDriverChromePath {

    public static void main(String[] args) throws InterruptedException, RuntimeException, MalformedURLException {

        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:\\Programowanie\\Narzędzia\\Browsers\\Chrome 137.0.7151.119\\chrome-win64");

        //WebDriver driver = new ChromeDriver(options);


/*
        URL seleniumURL = new URL("http://localhost:4444/");
        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("webdriver.remote.server", seleniumURL);

//        RemoteWebDriver driver = new RemoteWebDriver(seleniumURL, options);
      //  capabilities.setBrowserName("chrome");
        capabilities.setCapability("browserName", "firefox");
        String serverUrl = System.getProperty("webdriver.remote.server");
//        String serverUrl = System.getenv("webdriver.remote.server");

        if (serverUrl == null) {
            throw new IllegalArgumentException("Właściwość 'webdriver.remote.server' nie została ustawiona");
        }


        WebDriver driver = new RemoteWebDriver(new URL(serverUrl), capabilities);*/
        ChromeOptions chromeOptions = new ChromeOptions();

        // 2. Dodaj argument pozwalający na połączenia WebSocket z dowolnego źródła
        chromeOptions.addArguments("--remote-allow-origins=*");

        // Opcjonalnie: Możesz ustawić dodatkowe capability, aby widzieć nazwę testu w UI Grida

        chromeOptions.setCapability("se:name", "Mój test z Chrome"); // [8]
        options.setCapability("se:cdp", true); // Enable WebSocket-based BiDi commands
        options.setExperimentalOption("w3c", true);
        options.setCapability("webSocketUrl", true);





        URL seleniumURL = new URL("http://localhost:4444");
        URL seleniumServerUrl = new URL("http://localhost:4444/");
        /*WebDriver driver = WebDriverManager.chromedriver()
                .remoteAddress(seleniumServerUrl).create();


       *//* WebDriver driver = RemoteWebDriver.builder().oneOf(chromeOptions).address(seleniumURL).build();*/


        chromeOptions.addArguments("--remote-allow-origins=*");

// Teraz builder powinien zadziałać
      /*  WebDriver driver = RemoteWebDriver.builder()
                .oneOf(chromeOptions)
                .address(seleniumURL)
                .build();*/
        WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker();
        WebDriver driver = wdm.create();


        driver.get("https://www.google.pl/");
        TimeUnit.SECONDS.sleep(2);
        System.out.println(driver.getTitle());
        driver.quit();


    }





}
