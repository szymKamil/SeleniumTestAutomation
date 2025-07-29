package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class IFramePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;

    public IFramePage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "my-iframe")
    public WebElement iFrame;

    public void switchToiFrame(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
    }

    public void scrollPage(){
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollPage(int heightToScroll){
        js.executeScript("window.scrollTo(0, %d);".formatted(heightToScroll));
    }


}
