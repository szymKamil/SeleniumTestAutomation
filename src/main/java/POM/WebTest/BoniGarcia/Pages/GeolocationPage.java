package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.Optional;

public class GeolocationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Actions actions;
    DevTools devTools;

    public GeolocationPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        devTools = ((HasDevTools) driver).getDevTools();
    }

    @FindBy(id = "get-coordinates")
    WebElement geolocationBtn;

    @FindBy(id = "coordinates")
    WebElement coordinatesParagraph;


    public void clickGeolocationBtn(){
        geolocationBtn.click();
    }

    public void setCoordinates(double latitude, double longitude){
        devTools.createSession();
        setCoordinates(latitude, longitude, 1, 0, 0, 0, 0);
        devTools.close();
    }

    public void setCoordinates(double latitude, double longitude, int accuracy, int altitude, int altitudeAccuracy, int heading, int speed){
        devTools.createSession();
        devTools.send(Emulation.setGeolocationOverride(Optional.of(latitude), Optional.of(longitude), Optional.of(accuracy),
                Optional.of(altitude),
                Optional.of(altitudeAccuracy),
                Optional.of(heading),
                Optional.of(speed)));
        devTools.close();

    }

    public  String returnCoordinates(){
        String coordinates = coordinatesParagraph.getText();
        coordinates = coordinates.replace("\n", " ");
        return coordinates;
    }

}
