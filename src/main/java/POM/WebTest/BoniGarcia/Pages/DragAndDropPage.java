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

public class DragAndDropPage extends AbstractPage {

    Actions actions;

    public DragAndDropPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    //Elementy na stronie
    @FindBy(id = "draggable")
    public WebElement draggablePanel;

    @FindBy(id = "target")
    public WebElement targetElement;

    //Metody testowe
    public void dragPanelTo(){
        var x = targetElement.getLocation().getX();
        var y = targetElement.getLocation().getY();
        dragPanelTo(x, y);
        log.info("Panel został przeniesiony do domyślnego miejsca: X: {} Y: {}", x ,y);
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

    public void dragPanelTo(Point point){
        wait.until(ExpectedConditions.visibilityOf(draggablePanel));
        wait.until(ExpectedConditions.elementToBeClickable(targetElement));

        Dimension panelSize = draggablePanel.getSize();
        int panelCenterX = getElementCoords().getX() + panelSize.getWidth() / 2;
        int panelCenterY = getElementCoords().getY() + panelSize.getHeight() / 2;

        Dimension targetSize = targetElement.getSize();
        int targetCenterX = point.getX() + targetSize.getWidth() / 2;
        int targetCenterY = point.getY() + targetSize.getHeight() / 2;

        int xOffset = targetCenterX - panelCenterX;
        int yOffset = targetCenterY - panelCenterY;

        actions.clickAndHold(draggablePanel)
                .moveByOffset(xOffset, yOffset)
                .release()
                .build()
                .perform();
    }

    //Gettery
    public Point getElementCoords(){
        var x = draggablePanel.getLocation().getX();
        var y = draggablePanel.getLocation().getY();
        log.info("Koordynaty panelu to: {} i {} ", x, y);
        return new Point(x, y);
    }

    public Point getTargetCoords(){
        var x = targetElement.getLocation().getX();
        var y = targetElement.getLocation().getY();
        log.info("Koordynaty miejsca docelowego to: {} i {} ", targetElement.getLocation().x, targetElement.getLocation().y);
        return new Point(x, y);
    }

}
