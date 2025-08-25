package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v137.storage.Storage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.Objects;

public class WebStoragePage extends AbstractPage{


    private JavascriptExecutor js;
    DevTools devTools;

    public WebStoragePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        devTools = ((HasDevTools) this.driver).getDevTools();
        PageFactory.initElements(this.driver, this);
        js = (JavascriptExecutor) this.driver;
    }

    //Elementy na stronie
    @FindBy(id = "display-local")
    WebElement LOCAL_STORAGE_BTN;

    @FindBy(id = "display-session")
    WebElement LOCAL_SESSION_BTN;

    @FindBy(id = "local-storage")
    WebElement LOCAL_STORAGE_PARAGRAPH;

    @FindBy(id = "session-storage")
    WebElement LOCAL_SESSION_PARAGRAPH;

    //Metody testowe
    public void clickLocalStorageBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(LOCAL_STORAGE_BTN)).click();
    }

    public void clickLocalSessionBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(LOCAL_SESSION_BTN)).click();
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
        return wait.until(ExpectedConditions.visibilityOf(LOCAL_STORAGE_PARAGRAPH)).getText();
    }

    public String getTextFormSessionParagraph(){
        return wait.until(ExpectedConditions.visibilityOf(LOCAL_SESSION_PARAGRAPH)).getText();
    }

}
