package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoadingImagesPage  extends AbstractPage{


    Actions actions;
    FluentWait<WebDriver> fluentWait;

    public LoadingImagesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
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
        public WebElement ladscapeImage;

        @FindBy(id = "text")
        public WebElement confirmationParagraphText;


        //Metody testowe
        public void waitForImagesToLoad(){
            List<WebElement> imagesList = new ArrayList<>(List.of(compassImage, calendarImage, awardImage, ladscapeImage));
            log.info("Czekam na załadowanie obrazów...");
            fluentWait.until(ExpectedConditions.visibilityOfAllElements(imagesList));
            log.info("Obrazy zostały załadowane!");
        }

        public void confirmElementsVisibility(){
            wait.until(ExpectedConditions.textToBePresentInElement(confirmationParagraphText, "Done!"));
            log.info("Wszystkie elementy są widoczne i poprawnie zostały załadowane.");
        }


}
