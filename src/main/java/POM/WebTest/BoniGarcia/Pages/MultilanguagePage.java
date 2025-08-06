package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MultilanguagePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Actions actions;
    DevTools devTools;
    JavascriptExecutor js;

    public MultilanguagePage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);

    }

    @FindBy(id = "content")
    WebElement contentParagraphs;


    public String getParagraphsInfo(){
        return wait.until(ExpectedConditions.visibilityOf(contentParagraphs)).getText().replace("\n", ", ");
    }


}
