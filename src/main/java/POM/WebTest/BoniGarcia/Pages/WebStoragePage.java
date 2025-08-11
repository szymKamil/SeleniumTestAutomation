package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v137.domstorage.DOMStorage;
import org.openqa.selenium.devtools.v137.domstorage.model.StorageId;
import org.openqa.selenium.devtools.v137.storage.Storage;
import org.openqa.selenium.devtools.v138.accessibility.Accessibility;
import org.openqa.selenium.devtools.v138.css.CSS;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.net.URI;
import java.util.Objects;

public class WebStoragePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;
    DevTools devTools;

    public WebStoragePage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        devTools = ((HasDevTools) driver).getDevTools();
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    //Elementy na stronie
    @FindBy(id = "display-local")
    public WebElement localStorageBtn;

    @FindBy(id = "display-session")
    public WebElement localSessionBtn;

    @FindBy(id = "local-storage")
    public WebElement localStorageParagraph;

    @FindBy(id = "session-storage")
    public WebElement localSessionParagraph;

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

}
