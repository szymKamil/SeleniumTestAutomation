package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.TimeStampGenerator;
import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UserMediaPage {


    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Actions actions;
    DevTools devTools;
    JavascriptExecutor js;

    public UserMediaPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        devTools = ((HasDevTools) driver).getDevTools();
        js = (JavascriptExecutor) driver;
    }

    //Elementy na stronie
    @FindBy(id = "start")
    WebElement startBtn;

    @FindBy(id = "video-device")
    WebElement videoDeviceInfo;

    //Metody testowe
    public void runUserMedia(){
        wait.until(ExpectedConditions.elementToBeClickable(startBtn)).click();
    }

    public String getVideoDeviceInfo(){
        return wait.until(ExpectedConditions.visibilityOf(videoDeviceInfo)).getText();
    }

    //TODO: do przeniesienia do uniwersalnych metod
    public void takeScreenshot(String testName) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path pathForScreens = Path.of("src/main/java/POM/WebTest/BoniGarcia/Screenshots").toAbsolutePath();
        String fileName = "screenshot_" + testName + TimeStampGenerator.generateDateTime() + ".png";
        File targetDirectory = pathForScreens.resolve( fileName).toFile();
        FileUtils.copyFile(screenshot, targetDirectory);
    }

}
