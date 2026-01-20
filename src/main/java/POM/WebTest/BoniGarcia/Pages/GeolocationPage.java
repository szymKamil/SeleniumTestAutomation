package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.emulation.GeolocationCoordinates;
import org.openqa.selenium.bidi.emulation.SetGeolocationOverrideParameters;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

public class GeolocationPage extends AbstractPage {


    Actions actions;
    DevTools devTools;
    BiDi bidi;

    public GeolocationPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        //devTools = ((HasDevTools) driver).getDevTools();
    }

    //Elementy strony
    @FindBy(id = "get-coordinates")
    WebElement geolocationBtn;

    @FindBy(id = "coordinates")
    WebElement coordinatesParagraph;


    //Metody testowe
    public void setDevToolsOrBidi(){
        WebDriver instancedDriver = DriverFactory.getDriver();
        if (instancedDriver instanceof HasDevTools instance){
            devTools = instance.getDevTools();
        } else if (instancedDriver instanceof HasBiDi instance) {
            bidi = instance.getBiDi();
        }
    }


    public void clickGeolocationBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(geolocationBtn)).click();
    }

    public void setCoordinates(double latitude, double longitude){
        setCoordinates(latitude, longitude, 1, 0, 0, 0, 0);
    }


    public void setCoordinates(double latitude, double longitude, int accuracy, int altitude, int altitudeAccuracy, int heading, int speed){
        if (devTools != null) {
            devTools.createSession();
            devTools.send(Emulation.setGeolocationOverride(Optional.of(latitude), Optional.of(longitude),
                    Optional.of(accuracy),
                    Optional.of(altitude),
                    Optional.of(altitudeAccuracy),
                    Optional.of(heading),
                    Optional.of(speed)));
        } else if (bidi != null) {
            var contextDriver = DriverFactory.getDriver();
            String userContextId = "default";
            org.openqa.selenium.bidi.emulation.Emulation emulation = new org.openqa.selenium.bidi.emulation.Emulation(contextDriver);
            GeolocationCoordinates coordinates = new GeolocationCoordinates(latitude, longitude);
            SetGeolocationOverrideParameters params = new SetGeolocationOverrideParameters(coordinates)
                    .userContexts(List.of(userContextId));
            emulation.setGeolocationOverride(params);
        }

    }

    public  String returnCoordinates(){
        String coordinates = coordinatesParagraph.getText();
        coordinates = coordinates.replace("\n", " ");
        return coordinates;
    }

}
