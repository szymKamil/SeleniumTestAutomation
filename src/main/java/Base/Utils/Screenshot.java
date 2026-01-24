package Base.Utils;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Screenshot {

    private static final Logger log = DriverFactory.getLogger();

    /**
     * Utils pozwalający na tworzenie screenshotów w trakcie testu.
     */
    public static void takeScreenshot(){
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof WrapsDriver wrapsDriver){
            driver = wrapsDriver.getWrappedDriver();
        }
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        Path directoryPath = Path.of("Screenshots/test_%s_id%s".formatted(Utils.getCurrentDate(), sessionId.toString()));
        try {
            Files.createDirectory(directoryPath);
            log.info("Utworzony został folder {} na screenshoty z testu {}", directoryPath, sessionId);
        } catch (IOException e) {
            log.error("Wystąpił problem ze stworzeniem folderu  {}, {}", directoryPath, e.getMessage());
        }
        String screenshotName = String.format("%s/%s-%s.png", directoryPath, sessionId, TimeStampGenerator.generateDateTime());
        Path destination = Paths.get(screenshotName);
        try {
            Files.move(screenshot.toPath(), destination);
            log.info("Screenshot został zapisany {}", destination);
        } catch (IOException e){
            log.error("Problem z przeniesieniem screenshota z {} do {}", screenshot, screenshotName);
        }
    }



}
