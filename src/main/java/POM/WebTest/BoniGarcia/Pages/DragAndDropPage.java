package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.Arrays;

public class DragAndDropPage extends AbstractPage {

    Actions actions;

    public DragAndDropPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(id = "draggable")
    public WebElement DRAGGABLE_PANEL;

    @FindBy(id = "target")
    public WebElement TARGET_ELEMENT;

    //Metody testowe
    public void dragPanelTo(){
        var x = TARGET_ELEMENT.getLocation().getX();
        var y = TARGET_ELEMENT.getLocation().getY();
        dragPanelTo(x, y);
        log.info("Panel został przeniesiony do domyślnego miejsca: X: {} Y: {}", x ,y);
    }

    public void dragPanelTo(int xCoordinate, int yCoordinate){
        wait.until(ExpectedConditions.elementToBeClickable(DRAGGABLE_PANEL));
        actions.clickAndHold(DRAGGABLE_PANEL).moveToLocation(xCoordinate, yCoordinate).release().build().perform();
    }

    public void dragPanelTo(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(DRAGGABLE_PANEL));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        actions.clickAndHold(DRAGGABLE_PANEL).moveToElement(element).release().build().perform();
    }

    public void dragPanelTo(Point point){
        wait.until(ExpectedConditions.visibilityOf(DRAGGABLE_PANEL));
        wait.until(ExpectedConditions.elementToBeClickable(TARGET_ELEMENT));

        Dimension panelSize = DRAGGABLE_PANEL.getSize();
        int panelCenterX = getElementCoords().getX() + panelSize.getWidth() / 2;
        int panelCenterY = getElementCoords().getY() + panelSize.getHeight() / 2;

        Dimension targetSize = TARGET_ELEMENT.getSize();
        int targetCenterX = point.getX() + targetSize.getWidth() / 2;
        int targetCenterY = point.getY() + targetSize.getHeight() / 2;

        int xOffset = targetCenterX - panelCenterX;
        int yOffset = targetCenterY - panelCenterY;

        actions.clickAndHold(DRAGGABLE_PANEL)
                .moveByOffset(xOffset, yOffset)
                .release()
                .build()
                .perform();
    }

    //Gettery
    public Point getElementCoords(){
        var x = DRAGGABLE_PANEL.getLocation().getX();
        var y = DRAGGABLE_PANEL.getLocation().getY();
        log.info("Koordynaty panelu to: {} i {} ", x, y);
        return new Point(x, y);
    }

    public Point getTargetCoords(){
        var x = TARGET_ELEMENT.getLocation().getX();
        var y = TARGET_ELEMENT.getLocation().getY();
        log.info("Koordynaty miejsca docelowego to: {} i {} ", TARGET_ELEMENT.getLocation().x, TARGET_ELEMENT.getLocation().y);
        return new Point(x, y);
    }

}
