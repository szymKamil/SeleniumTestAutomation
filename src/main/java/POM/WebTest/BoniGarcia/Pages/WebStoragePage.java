package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v137.domstorage.DOMStorage;
import org.openqa.selenium.devtools.v137.domstorage.model.StorageId;
import org.openqa.selenium.devtools.v137.storage.Storage;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.net.URI;

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
    }

    @FindBy(id = "display-local")
    public WebElement localStorageBtn;

    @FindBy(id = "display-session")
    public WebElement localSessionBtn;

    @FindBy(id = "local-storage")
    public WebElement localStorageParagraph;

    @FindBy(id = "local-storage")
    public WebElement localSessionParagraph;


    public void clickLocalStorageBtn(){
        wait.until(ExpectedConditions.visibilityOf(localStorageBtn)).click();
    }

    public void clickLocalSessionBtn(){
        wait.until(ExpectedConditions.visibilityOf(localSessionBtn)).click();
    }

    public void clearLocalStorage(){
        devTools.createSession();
        devTools.send(Storage.clearDataForOrigin(driver.getCurrentUrl(), "local_storage"));

    }

    public void clearSessionStorage(){
        devTools.createSession();
        devTools.send(Storage.clearDataForOrigin(driver.getCurrentUrl(), "shared_storage"));


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("""
                let existing = sessionStorage.getItem('notes') || '';
                localStorage.setItem('notes', existing + ' | nowy wpis');""");

        URI uri = URI.create(driver.getCurrentUrl());
        String origin = uri.getScheme() + "://" + uri.getHost();
        log.info("Origin to: {}", origin);

        StorageId storageId = new StorageId(java.util.Optional.of(origin), java.util.Optional.empty(), false);
        devTools.send(DOMStorage.setDOMStorageItem(storageId, "Klucz", "Wartość"));

    }


}
