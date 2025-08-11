package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class FramesPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;

    public FramesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
    }

    //Elementy strony
    @FindBy(name = "frame-header")
    public WebElement frameHeader;

    @FindBy(name = "frame-body")
    public WebElement frameBody;

    @FindBy(name = "frame-footer")
    public WebElement frameFooter;

    //Metody testowe
    public void switchToFrame(WebElement element){
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
    }





}
