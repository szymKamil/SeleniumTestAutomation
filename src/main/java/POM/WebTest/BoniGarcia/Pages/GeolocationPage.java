package POM.WebTest.BoniGarcia.Pages;

import Base.DevTools.DevToolsFactory;
import Base.Drivers.DriverFactory;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.Command;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.emulation.GeolocationCoordinates;
import org.openqa.selenium.bidi.emulation.SetGeolocationOverrideParameters;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.browser.Browser;
import org.openqa.selenium.devtools.v142.browser.model.PermissionType;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GeolocationPage extends AbstractPage {


    private static final Logger log = LoggerFactory.getLogger(GeolocationPage.class);
    Actions actions;
    BiDi bidi;
    DevTools devtools;

    public GeolocationPage() {
        super();
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }

    //Elementy strony
    @FindBy(id = "get-coordinates")
    WebElement geolocationBtn;

    @FindBy(id = "coordinates")
    WebElement coordinatesParagraph;


    //Metody testowe
    public void setDevToolsOrBidi(){
        WebDriver instancedDriver = DriverFactory.getDriver();
        if (instancedDriver instanceof HasDevTools){
            devtools = DevToolsFactory.createSession(DriverFactory.getDriver());
        } else if (instancedDriver instanceof HasBiDi hasBiDi) {
            bidi = hasBiDi.getBiDi();
        }
    }


    public void clickGeolocationBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(geolocationBtn)).click();
    }

    public void setCoordinates(double latitude, double longitude){
        String browserName = ((HasCapabilities) driver).getCapabilities().getBrowserName().toLowerCase();
        //Driver może być opakowany, stąd ten sposób
        if (browserName.contains("edge") || browserName.contains("msedge")) {
            ArrayList<PermissionType> permissions = new ArrayList<>(List.of(PermissionType.GEOLOCATION));
            devtools.send(Browser.grantPermissions(
                    permissions,
                    Optional.empty(),
                    Optional.empty()
            ));
        }
        setCoordinates(latitude, longitude, 1, 0, 0, 0, 0);

    }


    public void setCoordinates(double latitude, double longitude, int accuracy, int altitude, int altitudeAccuracy, int heading, int speed) {
        if (devtools != null) {
            DevToolsFactory.setCoordinates(latitude, longitude, accuracy, altitude, altitudeAccuracy, heading, speed);
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
