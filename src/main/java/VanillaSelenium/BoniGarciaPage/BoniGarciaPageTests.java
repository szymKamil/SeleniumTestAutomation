/***
 * W klasie znajdują się vanila testy Selenium + TestNG (uproszczone użycie). Uruchamiane są jedynie w przeglądarce Chrome.
 *
 */


package VanillaSelenium.BoniGarciaPage;

import ch.qos.logback.core.spi.LogbackLock;
import ch.qos.logback.core.util.FileUtil;
import com.google.common.collect.ImmutableList;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.hc.core5.util.Asserts;
import org.openqa.selenium.*;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.*;
import org.openqa.selenium.devtools.idealized.target.model.TargetInfo;
import org.openqa.selenium.devtools.v135.browser.model.Bucket;
import org.openqa.selenium.devtools.v135.dom.DOM;
import org.openqa.selenium.devtools.v137.browser.model.BrowserContextID;
import org.openqa.selenium.devtools.v137.domstorage.DOMStorage;
import org.openqa.selenium.devtools.v137.domstorage.model.DomStorageItemAdded;
import org.openqa.selenium.devtools.v137.domstorage.model.Item;
import org.openqa.selenium.devtools.v137.domstorage.model.StorageId;
import org.openqa.selenium.devtools.v137.emulation.Emulation;
import org.openqa.selenium.devtools.v137.page.Page;
import org.openqa.selenium.devtools.v137.page.model.Frame;
import org.openqa.selenium.devtools.v137.page.model.FrameId;
import org.openqa.selenium.devtools.v137.page.model.FrameTree;
import org.openqa.selenium.devtools.v137.storage.model.StorageBucket;
import org.openqa.selenium.devtools.v137.storage.Storage;
import org.openqa.selenium.devtools.v137.storage.model.*;
import org.openqa.selenium.devtools.v137.target.Target;
import org.openqa.selenium.devtools.v137.target.model.TargetID;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ch.qos.logback.core.*;
import org.slf4j.Logger;
import org. openqa. selenium. devtools. idealized. target. model. TargetInfo;

import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.Assertions.*;
import org.openqa.selenium.devtools.DevTools;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BoniGarciaPageTests {

    ChromeOptions options = new ChromeOptions();


    WebDriver driver;
    WebDriverWait wait;
    Logger logger = LoggerFactory.getLogger(lookup().lookupClass());
    Actions actions;
    Random random = new Random(4);
    JavascriptExecutor js;
    LocalDateTime localDateTime = LocalDateTime.now();
    DevTools devTools;




    @BeforeClass
    public void webDriverInitialize() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        options.setExperimentalOption("prefs", prefs);
        driver = WebDriverManager.chromedriver().capabilities(options).create();

        WebDriverManager.chromedriver()
                .setup();
        options.addArguments("--kiosk");
        /* wyłączone ze względu na ExpliciteWait
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(10));*/
        wait =  new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
        devTools = ((HasDevTools) driver).getDevTools();
    }


    @AfterClass
    public void end(){
        driver.quit();
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

    @Test
    void test4(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        assertThat(driver.findElement(By.cssSelector("h1.display-6")).getText()).isEqualTo("Dropdown menu");
        actions = new Actions(driver);
        WebElement dropdownBtn1 = driver.findElement(By.id("my-dropdown-1"));
        actions.click(dropdownBtn1).build().perform();
        assertThat(driver.findElement(By.xpath("//ul[@class='dropdown-menu show']")).isDisplayed()).isTrue();
        WebElement dropdownBtn2 = driver.findElement(By.id("my-dropdown-2"));
        actions.contextClick(dropdownBtn2).build().perform();
        assertThat(driver.findElement(By.id("context-menu-2")).isDisplayed()).isTrue();
        WebElement dropdownBtn3 = driver.findElement(By.id("my-dropdown-3"));
        actions.doubleClick(dropdownBtn3).build().perform();
        assertThat(driver.findElement(By.id("context-menu-3")).isDisplayed()).isTrue();
    }

    @Test
    void test5(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
        List<WebElement> imgList = driver.findElements(By.cssSelector("div > img.img-fluid"));
        String[] imgCaptions = {"Compass", "Calendar", "Award", "Landscape"};
        actions = new Actions(driver);
        for (int i = 0; i < imgList.size(); i++){
            actions.moveToElement(imgList.get(i)).build().perform();
            assertThat(driver.findElement(By.xpath(String.format("//*[text()='%s']", imgCaptions[i]))).isDisplayed()).isTrue();
        }
    }

    @Test
    void test6(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
        assertThat(driver.findElement(By.xpath("//h1[text()='Drag and drop']")).isDisplayed()).isTrue();
        WebElement draggablePanel = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("target"));
        Point elementLocal = draggablePanel.getLocation();
        actions = new Actions(driver);
        actions.clickAndHold(draggablePanel).moveToElement(target).build().perform();
        Point movedLocal = draggablePanel.getLocation();
        assertThat(movedLocal).isNotEqualTo(elementLocal);
        logger.info("Pierwonta lokalizacja elementu to: {}, po przeniesieniu znajduje się w miejscu: {}", elementLocal, movedLocal);

    }

    @Test
    void test7(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html");
        WebElement canvas = driver.findElement(By.id("my-canvas"));
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('height', 700)", canvas);
        assertThat(canvas.getDomAttribute("height")).isEqualTo("700");
        actions = new Actions(driver);
        int canvasWidth = canvas.getSize().getWidth();
        int canvasHeight = canvas.getSize().getHeight();

        int centerX = canvasWidth - 100;
        int centerY = canvasHeight - 500;
        int scale = 10;

        List<Point> heartCoords = HeartCoordinatesGenerator.generateHeartPoints(centerX, centerY, scale, 50);
        Point canvasLocation = canvas.getLocation();
        Point prev = heartCoords.get(0);
        int startX = prev.x - canvasLocation.getX();
        int startY = prev.y - canvasLocation.getY();

        Actions actions = new Actions(driver);
        actions.moveToElement(canvas, startX, startY).clickAndHold();

        for (int i = 1; i < heartCoords.size(); i++) {
            Point current = heartCoords.get(i);
            int dx = current.x - prev.x;
            int dy = current.y - prev.y;
            actions.moveByOffset(dx, dy);
            prev = current;
        }
        actions.release().build().perform();
    }


    public class HeartCoordinatesGenerator {
        public static List<Point> generateHeartPoints(int centerX, int centerY, int scale, int numPoints) {
            List<Point> points = new ArrayList<>();
            for (int i = 0; i <= numPoints; i++) {
                double t = 2 * Math.PI * i / numPoints;
                double x = 16 * Math.pow(Math.sin(t), 3);
                double y = 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);
                int px = centerX + (int)(x * scale);
                int py = centerY - (int)(y * scale);
                points.add(new Point(px, py));
            }
            return points;
        }
    }

    @Test
    void test8(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).pollingEvery(Duration.ofSeconds(3))
                .withMessage("Czekam na element")
                .withTimeout(Duration.ofSeconds(45));
        fluentWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("div > img"), 4));
        WebElement landscapeImg = driver.findElement(By.cssSelector("div > img#landscape"));
        assertThat(landscapeImg.isDisplayed()).isTrue();

    }

    @Test
    void test9SlowCalculator(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
        WebElement header = driver.findElement(By.cssSelector("h1.display-6"));
        assertThat(header.getText()).isEqualTo("Slow calculator");
        WebElement calculator = driver.findElement(By.id("calculator"));
        WebElement delayInput = driver.findElement(By.id("delay"));
        useBtn("5", calculator);
        useBtn("+", calculator);
        useBtn("6", calculator);
        useBtn("-", calculator);
        useBtn("8", calculator);
        useBtn("=", calculator);
        WebElement spinner = driver.findElement(By.id("spinner"));
        logger.info("Kalkulator będzie czekać na wynik {} sekund", delayInput.getAttribute("value"));
        wait.until(ExpectedConditions.visibilityOf(spinner));
        wait.until(ExpectedConditions.invisibilityOf(spinner));
        logger.info("Wynik działania to: {}", driver.findElement(By.cssSelector("div.screen")).getText());
    }

    public static void useBtn(String x, WebElement e) {
        e.findElement(By.xpath(String.format("//*[text()='%s']", x))).click();
    }
    @Test
    void test10LongPage() throws IOException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        WebElement header = driver.findElement(By.cssSelector("h1.display-6"));
        assertThat(header.getText()).isEqualTo("This is a long page");
        actions = new Actions(driver);
        WebElement lastParagraph = driver.findElement(By.cssSelector("p:last-of-type"));
        actions.scrollToElement(lastParagraph);
//        File screenshot = wait.until(ExpectedConditions.visibilityOf(lastParagraph)).getScreenshotAs(OutputType.FILE);
        assertThat(lastParagraph.isDisplayed()).isTrue();
         TakesScreenshot ts = (TakesScreenshot) driver;
//      File screenshot = ts.getScreenshotAs(OutputType.FILE);
        File screenshot = lastParagraph.getScreenshotAs(OutputType.FILE);
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm");
        String formattedDate = dt.format(localDateTime);

        Path destination = Paths.get(formattedDate.concat("--").concat(String.valueOf(random.nextInt())).concat(".png"));
        try {
            Files.move(screenshot.toPath(), destination, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test11InfiniteScroll(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
        WebElement header = driver.findElement(By.cssSelector("h1.display-6"));
        assertThat(header.getText()).isEqualTo("Infinite scroll");
        int scrollTimes = 5;
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));


        String getPageHeight = "return document.body.scrollHeight";
        Long initialHeight = (Long) js.executeScript(getPageHeight);
        for (int i = 0; i < scrollTimes; i++){
            int numberOfParagraphs = driver.findElements(By.tagName("p")).size();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("p"), numberOfParagraphs));
        }
        Long endinglHeight = (Long) js.executeScript(getPageHeight);

        logger.info("Początkowa wysokość strony to {}, a końcowa wysokość to {}", initialHeight, endinglHeight );
    }

    @Test
    void test12ShadowDOM(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html");
        WebElement header = driver.findElement(By.cssSelector("h1.display-6"));
        assertThat(header.getText()).isEqualTo("Shadow DOM");
        List<WebElement> elems = driver.findElements(By.xpath("//p[text()='Hello Shadow DOM']"));
        assertThat(elems).isEmpty();
        WebElement shadowHost  = driver.findElement(By.id("content"));
        SearchContext shadowParagraph = shadowHost.getShadowRoot();
        String shadowParagraphText = shadowParagraph.findElement(By.cssSelector("p")).getText();
        logger.info("Treść shadowelementu to: {}", shadowParagraphText);

    }

    @Test
    void test13Cookies(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
        WebElement header = driver.findElement(By.xpath("//h1[text()='Cookies']"));
        wait.until(ExpectedConditions.visibilityOf(header));
        WebElement cookiesBtn = driver.findElement(By.id("refresh-cookies"));
        wait.until(ExpectedConditions.visibilityOf(cookiesBtn));
        cookiesBtn.click();
        WebElement paragraph = driver.findElement(By.id("cookies-list"));
        String cookiesInfo = paragraph.getText().trim().replace("\n", " ");
        logger.info("Dane widoczne w ciasteczkach: {}", cookiesInfo);
        Set<Cookie> cookies = driver.manage().getCookies();
        cookies.forEach(System.out::println);
//        Cookie username = cookies.stream().filter(c -> c.equals("username")).findAny().get();
//        logger.info("Ciasteczko ma dane = {}", username.getName());
        Cookie usename = driver.manage().getCookieNamed("username");
        logger.info(usename.toString());
        driver.manage().deleteCookie(usename);
        Cookie newUsernameCookie = new Cookie("username", "Jan Kowalski");
        driver.manage().addCookie(newUsernameCookie);
        cookiesBtn.click();
        cookiesInfo = paragraph.getText().trim().replace("\n", " ");
        logger.info("Dane widoczne w ciasteczkach: {}", cookiesInfo);

    }

    @Test
    void test14Frames(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/frames.html");
        String frameName = "frame-body";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(frameName)));
        driver.switchTo().frame("frame-header");
        WebElement header = driver.findElement(By.xpath("//h1[text()='Frames']"));
        wait.until(ExpectedConditions.visibilityOf(header));
        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-body");
        By pName = By.tagName("p");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pName, 0));
        List<WebElement> paragraphs = driver.findElements(pName);
        assertThat(paragraphs).hasSize(20);
    }

    @Test
    void test15iFrames(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");
        WebElement iFrame = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iFrame);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement lastP = driver.findElement(By.xpath("//p[last()]"));
        assertThat(lastP.getText()).contains("Magnis feugiat natoque proin commodo laoreet mauris, " +
                "odio ligula sagittis montes dapibus fames ultricies, interdum ridiculus volutpat aenean pulvinar.");

    }

    @Test
    void test16DialogBoxes(){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        WebElement header = driver.findElement(By.xpath("//h1[text()='Dialog boxes']"));
        wait.until(ExpectedConditions.visibilityOf(header));
        WebElement alertBtn = driver.findElement(By.id("my-alert"));
        WebElement confirmBtn = driver.findElement(By.id("my-confirm"));
        WebElement promptmBtn = driver.findElement(By.id("my-prompt"));
        WebElement modalBtn = driver.findElement(By.id("my-modal"));
        List<WebElement> buttons = List.of(alertBtn, confirmBtn, promptmBtn, modalBtn);
        wait.until(ExpectedConditions.visibilityOfAllElements(buttons));
        alertBtn.click();
        wait.until(ExpectedConditions.alertIsPresent());
        String alertBtnText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        confirmBtn.click();
        String confirmBtnText = driver.switchTo().alert().getText();
        driver.switchTo().alert().dismiss();
        promptmBtn.click();
        String promptBtnText = driver.switchTo().alert().getText();
        String promptMessaage =  "My name is John";
        driver.switchTo().alert().sendKeys(promptMessaage);
        driver.switchTo().alert().accept();
        WebElement promptMessageDisp = driver.findElement(By.id("prompt-text"));
        String promptMsg = promptMessageDisp.getText();
        modalBtn.click();
        WebElement modalWindow = driver.findElement(By.id("example-modal"));
        String modalText = modalWindow.getText();
        WebElement modalFormBtn = driver.findElement(By.cssSelector("button.btn-primary"));
        wait.until(ExpectedConditions.elementToBeClickable(modalFormBtn));
        modalFormBtn.click();
        logger.info("Po użyciu przycisków mamy następujące informacje: {}, {}, {}, {}, {}, {}", alertBtnText, confirmBtnText, promptBtnText, promptBtnText, promptMsg, modalText);
    }

    @Test
    void test17WebStorage() {
        String page = "https://bonigarcia.dev/selenium-webdriver-java/web-storage.html";
        driver.get(page);
        WebElement header = driver.findElement(By.xpath("//h1[text()='Web storage']"));
        wait.until(ExpectedConditions.visibilityOf(header));
        devTools.createSession();
        devTools.send(Storage.clearDataForOrigin(driver.getCurrentUrl(), "local_storage"));
        js.executeScript("""
                let existing = localStorage.getItem('notes') || '';
                localStorage.setItem('notes', existing + ' | nowy wpis');""");

        URI uri = URI.create(page);
        String origin = uri.getScheme() + "://" + uri.getHost();
        logger.info("Origin to: {}", origin);
     //   String originDisplayPath = "https://bonigarcia.dev";


        StorageId storageId = new StorageId(Optional.of(origin), Optional.empty(), true);
        devTools.send(DOMStorage.setDOMStorageItem(storageId, "Klucz", "Wartość"));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test18GeoLocal() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/geolocation.html");
        WebElement header = driver.findElement(By.xpath("//h1[text()='Geolocation']"));
        wait.until(ExpectedConditions.visibilityOf(header));
        devTools.createSession();
        //Ustawienie geolokalizacji za pomocą DevTools
        devTools.send(Emulation.setGeolocationOverride(Optional.of(1.9), Optional.of(1.7),
                Optional.of(1),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        driver.findElement(By.id("get-coordinates")).click();
        String coords = driver.findElement(By.id("coordinates")).getText();
        logger.info("Lokalizacja to: {}", coords);




    }
}









