package POM.WebTest.BoniGarcia.Pages;


import Base.Utils.PageLoadedVerification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MainPage extends AbstractPage {


    public MainPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
    }

    public static final By CONTAINERS = By.cssSelector("div.col-md-4, py-2");
    public static final By LEAD_SELECTOR = By.cssSelector("p.lead");
    public static final By COPYRIGHTS_SELECTOR = By.cssSelector("span.text-muted");
    public static final int EXPECTED_CONTAINERS_COUNT = 6;
    //Elementy
    public static final String BONI_GARCIA_MAIN_URL = "https://bonigarcia.dev/selenium-webdriver-java/";


    public WebElement getBtn(String btnName) {
        By btn = By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()='" + btnName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btn));
    }

    public void goToSubPage(String btnName) {
        wait.until(ExpectedConditions.elementToBeClickable(getBtn(btnName))).click();
    }

    public void openMainPage(){
        LocalDateTime start = LocalDateTime.now();
        driver.get(BONI_GARCIA_MAIN_URL);
        wait.until(PageLoadedVerification.pageIsLoaded());
        LocalDateTime stop = LocalDateTime.now();
        double time = Duration.between(start, stop).toNanos() / 1_000_000_000.0;
        log.info("Czas potrzebny na załadowanie strony wynosi {} sekund", time);
    }

    public void verifyHomePageContent() {
        verifyUrl();
        verifyMainHeader();
        verifyImage();
        verifyContainers();
        verifyLead();
        verifyCopyrights();
    }

    private void verifyUrl() {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl)
                .contains(BONI_GARCIA_MAIN_URL)
                .withFailMessage("Niepoprawny adres URL: %s", currentUrl);
    }

    private void verifyMainHeader() {
        WebElement header = getMainHeader();
        assertThat(header.isDisplayed()).isTrue();
        assertThat(header.getText()).isEqualTo(AbstractPage.PAGE_TITLE);
    }

    private void verifyImage() {
        assertThat(getImage().isDisplayed()).isTrue();
    }

    private void verifyContainers() {
        assertThat(driver.findElements(CONTAINERS).size()).isEqualTo(EXPECTED_CONTAINERS_COUNT);
    }

    private void verifyLead() {
        WebElement leadElement = getLead();
        assertThat(leadElement.isDisplayed()).isTrue();
        assertThat(leadElement.getText()).isEqualTo(AbstractPage.LEAD_TEXT);
    }

    private void verifyCopyrights() {
        assertThat(getCopySpan().getText()).contains(COPYRIGHTS);
    }

    // Gettery
    private WebElement getMainHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_HEADER));
    }

    private WebElement getImage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_ICON));
    }

    private WebElement getLead() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LEAD_SELECTOR));
    }

    private WebElement getCopySpan() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(COPYRIGHTS_SELECTOR));
    }
}

