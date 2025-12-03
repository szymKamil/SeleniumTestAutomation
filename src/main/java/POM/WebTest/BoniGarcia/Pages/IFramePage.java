package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IFramePage extends AbstractPage{


    private JavascriptExecutor js;

    public IFramePage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    //Elementy na stronie
    @FindBy(id = "my-iframe")
    WebElement iFrame;

    @FindBy(xpath = "//p[last()]")
    WebElement lastParagraph;


    //Metody testowe
    public void switchToiFrame(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
    }

    public void scrollPage(){
        scrollPage("document.body.scrollHeight");
    }

    public void scrollPage(String heightToScroll){
        js.executeScript("window.scrollTo(0, " + heightToScroll + ");");
    }

    public String getTextFromLastParagraph(){
       return wait.until(ExpectedConditions.visibilityOf(lastParagraph)).getText();
    }


}
