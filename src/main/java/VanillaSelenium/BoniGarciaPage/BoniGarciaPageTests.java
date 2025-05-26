/***
 * W klasie znajdują się vanila testy Selenium + TestNG (uproszczone użycie). Uruchamiane są jedynie w przeglądarce Chrome.
 *
 */


package VanillaSelenium.BoniGarciaPage;

import ch.qos.logback.core.spi.LogbackLock;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.hc.core5.util.Asserts;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ch.qos.logback.core.*;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.*;


import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BoniGarciaPageTests {

    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    Logger logger = LoggerFactory.getLogger(lookup().lookupClass());
    Random random = new Random();

    @BeforeClass
    public void webDriverInitialize() {
        WebDriverManager.chromedriver()
                .setup();
        driver = new ChromeDriver();
        /* wyłączone ze względu na ExpliciteWait
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(10));*/
    }


    @Test
    public void test1MainPage(){
    driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
    assertThat(driver.getTitle()).isEqualTo("Hands-On Selenium WebDriver with Java");
    logger.info("Otwieram stronę: {}", driver.getCurrentUrl());
    WebElement webFormPrzycisk = driver.findElement(By.xpath("//a[@href='web-form.html']"));
    assertThat(webFormPrzycisk).isNotNull();
    webFormPrzycisk.click();
    WebElement praticeSiteEtykieta = driver.findElement(By.xpath("//h5[text()='Practice site']"));
    wait.until(ExpectedConditions.visibilityOf(praticeSiteEtykieta));
    assertThat(praticeSiteEtykieta.isDisplayed()).isTrue();
    WebElement textInputPole = driver.findElement(By.id("my-text-id"));
    String testText = "Test";
    textInputPole.sendKeys(testText);
    logger.info("Wpisuję tekst: {}", testText);
    String inputString = textInputPole.getAttribute("value");
    assertThat(inputString).isEqualTo(testText);
    logger.info("Test ukończony");
    }

    @Test
    public void test2WebForm() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertThat(driver.getTitle()).isEqualTo("Hands-On Selenium WebDriver with Java");
        logger.info("Page title matched expected value using getTitle(): {}", driver.getTitle());
        assertThat(driver.findElement(By.cssSelector("h1.display-6")).isDisplayed()).isTrue();
        driver.findElement(By.cssSelector("input.form-control[name='my-text']")).sendKeys("Test formularza");
        logger.info("W formularzu wpisano: {}",  driver.findElement(By.cssSelector("input.form-control[name='my-text']")).getAttribute("value"));
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("hasło");
        logger.info("W formularzu wpisano: {}",  driver.findElement(By.cssSelector("input[type='password']")).getAttribute("value"));
        ArrayList<Character> tablicaLiter = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++){
            tablicaLiter.add(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ch++){
            tablicaLiter.add(ch);
        }
        tablicaLiter.add(' ');
        tablicaLiter.add(',');
        tablicaLiter.add('.');
        StringBuilder testTekst = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            testTekst.append(tablicaLiter.get(random.nextInt(tablicaLiter.size())));
        }
        driver.findElement(By.name("my-textarea")).sendKeys(testTekst);
        String randomValue = driver.findElement(By.name("my-textarea")).getAttribute("value");
        assertThat(randomValue.length()).isEqualTo(40);
        logger.info("Wygenerowano następujący ciąg znaków: {}", randomValue);
        assertThat(driver.findElement(By.cssSelector("input[readonly]")).isDisplayed()).isTrue();
        WebElement select1WebElem = driver.findElement(By.name("my-select"));
        Select select = new Select(select1WebElem);
        for (WebElement option : select.getOptions()) {
            if (option.getText().contains("Two")) {
                select.selectByVisibleText(option.getText());
                break;
            }
        }
        logger.info("W pierwszej liście wybrano {}", select1WebElem.getDomAttribute("value"));
        WebElement dataList = driver.findElement(By.name("my-datalist"));
        dataList.click();
        String dataListOptionvalue = driver.findElement(By.xpath("//datalist/option[4]")).getDomAttribute("value");
        assertThat(dataListOptionvalue).isNotNull();
        dataList.sendKeys(dataListOptionvalue);
        logger.info("Value attribute: {}", dataList.getAttribute("value"));
        WebElement checkbox1 = driver.findElement(By.cssSelector("input[id='my-check-1']"));
        assertThat(checkbox1.isSelected()).isTrue();
        WebElement checkbox2 = driver.findElement(By.cssSelector("input[id='my-check-2']"));
        assertThat(checkbox2.isSelected()).isFalse();
        WebElement radio2 = driver.findElement(By.id("my-radio-2"));
        radio2.click();
        assertThat(radio2.isSelected()).isTrue();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Color color = new Color(65, 45, 34, 2);
        color.asHex();
        WebElement colorPicker = driver.findElement(By.name("my-colors"));
        String initialColor = colorPicker.getDomProperty("value");
        js.executeScript("arguments[0].setAttribute('value', '%s')", colorPicker);
        logger.info("The final color is {}", color);
        assertThat(initialColor).isNotEqualTo(color);
        LocalDate dataTestowa = LocalDate.of(2025, 12, 12);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String sformatowanaData = dataTestowa.format(formatter);
        logger.info("Wpisana została data: {}", dataTestowa);
        driver.findElement(By.name("my-date")).sendKeys(sformatowanaData);
        String dataKalendarz = driver.findElement(By.cssSelector("th[colspan='5'].datepicker-switch")).getText();
        assertThat(dataKalendarz).isEqualTo("December 2025");
        assertThat(driver.findElement(By.cssSelector("td.active")).getText()).isEqualTo("12");
        WebElement slider = driver.findElement(By.name("my-range"));
        int initialValue = Integer.parseInt(Objects.requireNonNull(slider.getAttribute("value")));
        for (int i = 0; i <= 3; i++) {
            slider.sendKeys(Keys.ARROW_LEFT);
        }
        int newSliderValue = Integer.parseInt(Objects.requireNonNull(slider.getAttribute("value")));
        logger.info("Porównuję dwie wartości slidera: {} oraz {}", initialValue, newSliderValue);
        assertThat(initialValue).isNotEqualTo(newSliderValue);
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        assertThat(submitBtn.isDisplayed()).isTrue();
        submitBtn.click();
        WebElement formSubmitted = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        assertThat(formSubmitted.isDisplayed()).isTrue();
        WebElement receivedInfo = driver.findElement(RelativeLocator.with(By.xpath("//p[@class='lead']")).below(formSubmitted));
        assertThat(receivedInfo.getText()).isEqualTo("Received!");
    }

    @Test
    void test3(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation3.html");
        List<WebElement> aButtons= driver.findElements(By.cssSelector("a.page-link"));
        assertThat(aButtons.size()).isEqualTo(5);
        WebElement prevButton = driver.findElement(By.xpath("//a[text()='Previous']"));
        WebElement liPrevButton = driver.findElement(By.xpath("//a[text()='Previous']/.."));
        while (!liPrevButton.getAttribute("class").contains("disabled")) {
            prevButton.click();
            liPrevButton = driver.findElement(By.xpath("//a[text()='Previous']/.."));
            prevButton = driver.findElement(By.xpath("//a[text()='Previous']"));
        }
        assertThat(driver.findElement(By.cssSelector("p.lead")).getText()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
    }






    @AfterClass
    public void end(){
        driver.quit();
    }


}









