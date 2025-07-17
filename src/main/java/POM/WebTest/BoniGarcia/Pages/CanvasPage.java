package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class CanvasPage {


    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Actions actions;

    public CanvasPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    @FindBy(id = "my-canvas")
    public WebElement canvas;

    public void paintInCanvas(){
        wait.until(ExpectedConditions.visibilityOf(canvas));
        canvas.getLocation().getX();
        canvas.getLocation().getY();
        actions.moveToElement(canvas).clickAndHold().moveByOffset(20, 20).moveByOffset(0, 20).moveByOffset(-20, -60)
                .moveByOffset(10, -70).moveByOffset(-100, 40).release().build().perform();

    }



}
