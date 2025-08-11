package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoadingImagesPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Actions actions;
    FluentWait<WebDriver> fluentWait;

    public LoadingImagesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        fluentWait = new FluentWait<>(driver).pollingEvery(Duration.ofSeconds(2))
                .withTimeout(Duration.ofSeconds(30))
                .ignoring(NoSuchElementException.class)
                .withMessage("Obrazy nie zostały załadowane poprawnie.");
    }

        //Elementy na stronie
        @FindBy(id = "compass")
        public WebElement compassImage;

        @FindBy(id = "calendar")
        public WebElement calendarImage;

        @FindBy(id = "award")
        public WebElement awardImage;

        @FindBy(id = "landscape")
        public WebElement landscapeImage;

        @FindBy(id = "text")
        public WebElement paragraphText;


        //Metody testowe
        public void waitForImagesToLoad(){
            List<WebElement> imagesList = new ArrayList<>(List.of(compassImage, calendarImage, awardImage, landscapeImage));
            log.info("Czekam na załadowanie obrazów...");
            fluentWait.until(ExpectedConditions.visibilityOfAllElements(imagesList));
            log.info("Obrazy zostały załadowane!");
        }



}
