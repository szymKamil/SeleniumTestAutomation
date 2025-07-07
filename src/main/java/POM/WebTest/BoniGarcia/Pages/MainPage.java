package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Tests.BaseTest;
import POM.WebTest.BoniGarcia.Tests.MainTest;
import com.sun.jna.WString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;


public class MainPage  {
    WebDriver driver;
    WebDriverWait wait;
    Logger log ;


    public MainPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
    }
    public static final String pageTitle = "Hands-On Selenium WebDriver with Java";
    public static final String leadText = "This site contains a collection of sample web pages to be tested with Selenium WebDriver. " +
            "Check out the O'Reilly book and the source code on GitHub.";
    public static final String copyrights = "Copyright © 2021-2025";
    public static final String boniGarciaMainURL = "https://bonigarcia.dev/selenium-webdriver-java/";
    public static final By mainHeader = By.xpath("//h1[@class='display-4']");
    public static final By img = By.cssSelector("img.img-fluid");
    public static final By containers = By.cssSelector("div.col-md-4, py-2");
    public static final By lead = By.cssSelector("p.lead");
    public static final By copySpan = By.cssSelector("span.text-muted");

    public void verifyMainPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        assertThat(driver.findElement(img).isDisplayed()).isTrue();
        assertThat(driver.findElement(mainHeader).getText()).isEqualTo(pageTitle);
        assertThat(driver.findElements(containers).size()).isEqualTo(6);
        assertThat(driver.findElement(lead).isDisplayed());
        assertThat(driver.findElement(lead).getText()).isEqualTo(leadText);
        assertThat(driver.findElement(copySpan).getText()).contains(copyrights);
    }


    public WebElement getBtn(String btnName) {
        By btn = By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()='" + btnName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btn));
    }


    public void goToSubPage(String btnName) {
        WebElement btn = driver.findElement(By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()='" + btnName + "']"));
        wait.until(ExpectedConditions.visibilityOf(btn));
        btn.click();
    }



}