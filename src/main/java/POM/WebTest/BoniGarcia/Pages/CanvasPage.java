package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.PointForCanvas;
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


    public void paintInCanvas(PointForCanvas... points){
        wait.until(ExpectedConditions.visibilityOf(canvas));
        actions.moveToElement(canvas).clickAndHold();
        for(PointForCanvas point : points) {
            actions.moveByOffset(point.x(), point.y());
        }
        actions.release().build().perform();
    }


    public void paintInCanvas(){
        paintInCanvas(new PointForCanvas(20, 20), new PointForCanvas(0, 20), new PointForCanvas(20, -60),
                new PointForCanvas(10, -70), new PointForCanvas(-100, 40),new PointForCanvas(-100, 40),new PointForCanvas(0, 20));

    }

}
