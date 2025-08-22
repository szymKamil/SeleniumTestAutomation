package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.TimeStampGenerator;
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

public class UserMediaPage extends AbstractPage{

    Actions actions;
    DevTools devTools;
    JavascriptExecutor js;

    public UserMediaPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(driver, this);
        actions = new Actions(this.driver);
        devTools = ((HasDevTools) this.driver).getDevTools();
        js = (JavascriptExecutor) this.driver;
    }

    //Elementy na stronie
    @FindBy(id = "start")
    WebElement START_BTN;

    @FindBy(id = "video-device")
    WebElement VIDEO_DEVICE_INFO;

    //Metody testowe
    public void runUserMedia(){
        wait.until(ExpectedConditions.elementToBeClickable(START_BTN)).click();
    }

    public String getVIDEO_DEVICE_INFO(){
        return wait.until(ExpectedConditions.visibilityOf(VIDEO_DEVICE_INFO)).getText();
    }

}
