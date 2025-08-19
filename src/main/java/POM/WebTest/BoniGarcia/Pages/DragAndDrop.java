package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class DragAndDrop extends AbstractPage {

    Actions actions;

    public DragAndDrop(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(id = "draggable")
    public WebElement draggablePanel;

    @FindBy(id = "target")
    public WebElement target;

    //Metody testowe
    public void dragPanelTo(){
        dragPanelTo(target.getLocation().getX(), target.getLocation().getY());
    }

    public void dragPanelTo(int xCoordinate, int yCoordinate){
        wait.until(ExpectedConditions.elementToBeClickable(draggablePanel));
        actions.clickAndHold(draggablePanel).moveToLocation(xCoordinate, yCoordinate).release().build().perform();
    }

    public void dragPanelTo(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(draggablePanel));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        actions.clickAndHold(draggablePanel).moveToElement(element).release().build().perform();
    }
}
