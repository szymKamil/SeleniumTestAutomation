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


    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public static final By CONTAINERS = By.cssSelector("div.col-md-4.py-2");
    public static final int EXPECTED_CONTAINERS_COUNT = 6;
    //Elementy
    public static final String BONI_GARCIA_MAIN_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    public static final String LEAD_TEXT = "This site contains a collection of sample web pages to be tested with Selenium WebDriver. " +
            "Check out the O'Reilly book and the source code on GitHub.";

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
        verifyAbstractPage();
        verifyLead();
        verifyUrl();
        verifyContainers();
    }


    private void verifyLead() {
        WebElement leadElement = getLead();
        assertThat(leadElement.isDisplayed()).isTrue().withFailMessage("Nagłówek nie jest widoczny.");
        assertThat(leadElement.getText()).isEqualTo(LEAD_TEXT).withFailMessage("nagłówek ma błędny tekst w sobie. ");
    }


    private void verifyUrl() {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl)
                .isEqualTo(BONI_GARCIA_MAIN_URL)
                .withFailMessage("Niepoprawny adres URL: %s", currentUrl);
    }

    private WebElement getLead() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LEAD_SELECTOR));
    }


    private void verifyContainers() {
        assertThat(driver.findElements(CONTAINERS).size()).isEqualTo(EXPECTED_CONTAINERS_COUNT);
    }






}

