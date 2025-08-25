package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserMediaPage extends AbstractPage{

    Actions actions;
    DevTools devTools;
    JavascriptExecutor js;

    public UserMediaPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
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

}
