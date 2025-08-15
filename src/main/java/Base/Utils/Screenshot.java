package Base.Utils;

import POM.WebTest.BoniGarcia.Utils.TimeStampGenerator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Screenshot {

    private static final Logger log = LoggerFactory.getLogger(Screenshot.class);
    WebDriver driver;

    public void setDriver(WebDriver driver){
        this.driver = driver;
    }

    public static void takeScreenshot(WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        String screenshotName = String.format("Screenshots/%s-%s.png", sessionId.toString(), TimeStampGenerator.generateDateTime());
        Path destination = Paths.get(screenshotName);
        try {
            Files.move(screenshot.toPath(), destination);
            log.info("Screenshot został zapisany {}", destination);
        } catch (IOException e){
            log.error("Problem z przeniesieniem screenshota z {} do {}", screenshot, screenshotName);
        }

    }



}
