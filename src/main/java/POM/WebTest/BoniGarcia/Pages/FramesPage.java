package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FramesPage extends AbstractPage {

    Logger log = LoggerFactory.getLogger(FramesPage.class);


    public FramesPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
    }

    //Elementy strony
    @FindBy(name = "frame-header")
    WebElement frameHeader;

    @FindBy(name = "frame-body")
    WebElement frameBody;

    @FindBy(name = "frame-footer")
    WebElement frameFooter;

    //Metody testowe
    public void switchToFrame(String text){
        driver.switchTo().defaultContent();
        switch (text.toLowerCase()){
            case "header" -> wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameHeader));
            case "body" -> wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameBody));
            case "footer" -> wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameFooter));
            default -> throw new IllegalArgumentException("Błędnie wybrana ramka!");
        }
    }

    public void verifyVisibilityOfParagraphs(){
        int paragraphsSize = driver.findElements(By.cssSelector("p")).size();
        log.info("W teście po przełączeniu się na ramkę widocznych jest {} paragrafów", paragraphsSize);
    }

}
