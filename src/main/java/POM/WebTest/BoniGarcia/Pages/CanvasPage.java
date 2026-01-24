package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import POM.WebTest.BoniGarcia.Utils.PointForCanvas;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class CanvasPage extends AbstractPage{

    private final Actions actions;

    public CanvasPage() {
        super();
        PageFactory.initElements(DriverFactory.getDriver(), this);
        actions = new Actions(DriverFactory.getDriver());
    }

    //Elementy strony
    @FindBy(id = "my-canvas")
    public WebElement canvasElement;


    //Metody testowe
    public void paintInCanvas(PointForCanvas... points){
        wait.until(ExpectedConditions.visibilityOf(canvasElement));
        actions.moveToElement(canvasElement).clickAndHold().perform();
        for(PointForCanvas point : points) {
            actions.moveByOffset(point.x(), point.y()).perform();
        }
        actions.release().perform();
    }

    //Metoda domyślna, rysująca abstrakcyjny kształt na płótnie
    public void paintInCanvas(){
        paintInCanvas(new PointForCanvas(20, 20), new PointForCanvas(0, 20), new PointForCanvas(20, -60),
                new PointForCanvas(10, -70), new PointForCanvas(-100, 40),new PointForCanvas(-100, 40),new PointForCanvas(0, 20));

    }

}
