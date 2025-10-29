package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.storage.Storage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class WebStoragePage extends AbstractPage{


    private JavascriptExecutor js;
    DevTools devTools;

    public WebStoragePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        devTools = ((HasDevTools) driver).getDevTools();
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    //Elementy na stronie
    @FindBy(id = "display-local")
    WebElement localStorageBtn;

    @FindBy(id = "display-session")
    WebElement localSessionBtn;

    @FindBy(id = "local-storage")
    WebElement localStorageParagraph;

    @FindBy(id = "session-storage")
    WebElement localSessionParagraph;

    //Metody testowe
    public void clickLocalStorageBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(localStorageBtn)).click();
    }

    public void clickLocalSessionBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(localSessionBtn)).click();
    }

    public void clearLocalStorage(){
        devTools.createSession();
        devTools.send(Storage.clearDataForOrigin(Objects.requireNonNull(driver.getCurrentUrl()), "local_storage"));
        devTools.close();
    }

    public void clearSessionStorage(){
        js.executeScript("sessionStorage.clear()");
    }

    public void inputValueToLocalStorage(String key, String value) {
        js.executeScript("localStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public void inputValueToSessionStorage(String key, String value) {
        js.executeScript("sessionStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public String getTextFormStorageParagraph(){
        return wait.until(ExpectedConditions.visibilityOf(localStorageParagraph)).getText();
    }

    public String getTextFormSessionParagraph(){
        return wait.until(ExpectedConditions.visibilityOf(localSessionParagraph)).getText();
    }

}
