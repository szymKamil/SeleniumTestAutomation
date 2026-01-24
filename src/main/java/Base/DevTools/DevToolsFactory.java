package Base.DevTools;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DevToolsFactory {

	static org.openqa.selenium.devtools.DevTools devTools;

	public static DevTools createSession(WebDriver driver){
		if (driver instanceof HasDevTools hasDevTools){
			devTools = hasDevTools.getDevTools();
			devTools.createSession();
		}
		return devTools;
	}

	public static void setCoordinates(double latitude, double longitude){
		WebDriver driver = DriverFactory.getDriver();
		String origin = (String) ((JavascriptExecutor) driver).executeScript("return window.location.origin");
		if (driver instanceof EdgeDriver edgeDriver) {
			edgeDriver.executeCdpCommand("Browser.grantPermissions", Map.of(
					"origin", origin,
					"permissions", List.of("geolocation")
			));
		}
		setCoordinates(latitude, longitude, 1, 0, 0, 0, 0);
	}



	public static void setCoordinates(double latitude, double longitude, int accuracy, int altitude, int altitudeAccuracy, int heading, int speed){
		 if (devTools != null) {
			devTools.send(Emulation.setGeolocationOverride(Optional.of(latitude), Optional.of(longitude),
					Optional.of(accuracy),
					Optional.of(altitude),
					Optional.of(altitudeAccuracy),
					Optional.of(heading),
					Optional.of(speed)));
		}
	}
}
