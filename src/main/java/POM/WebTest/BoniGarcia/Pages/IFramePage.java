package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class IFramePage extends AbstractPage{


    private JavascriptExecutor js;

    public IFramePage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
        js = (JavascriptExecutor) this.driver;
    }

    //Elementy na stronie
    @FindBy(id = "my-iframe")
    WebElement iFRAME;

    @FindBy(xpath = "//p[last()]")
    WebElement LAST_PARAGRAPH;


    //Metody testowe
    public void switchToiFrame(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFRAME));
    }

    public void scrollPage(){
        scrollPage("document.body.scrollHeight");
    }

    public void scrollPage(String heightToScroll){
        js.executeScript("window.scrollTo(0, " + heightToScroll + ");");
    }

    public String getTextFromLastParagraph(){
       return wait.until(ExpectedConditions.visibilityOf(LAST_PARAGRAPH)).getText();
    }


}
