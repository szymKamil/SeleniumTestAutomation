package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class WebStoragePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;

    public WebStoragePage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
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






}
