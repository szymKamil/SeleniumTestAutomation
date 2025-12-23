package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import Base.Utils.JavaScriptUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LongPage extends AbstractPage{

    Logger log = LoggerFactory.getLogger(LongPage.class);
    private Actions actions;


    public LongPage(WebDriver driver, WebDriverWait wait) {
        super();
        actions = new Actions(this.driver);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(css = "p.lead")
    List<WebElement> leadsElements;

    private By LEAD = By.cssSelector("p.lead");

    //Metody testowe
    public String getTextFromParagraph(int i){
        String paragraphText = wait.until(ExpectedConditions.visibilityOfAllElements(leadsElements)).get(i).getText();
        log.info("Paragraf {} ma tekst {}", i, paragraphText);
        return paragraphText;

    }

    public void printAllParagraphs(){
        for (int i = 0; i < leadsElements.size(); i++){
            String paragraphText = getTextFromParagraph(i);
            log.info("Paragraf numer {} posiada tekst: {}", (i+1), paragraphText);
        }
    }

    public void scrollToParagraph(int number){
       actions.scrollToElement(leadsElements.get(number)).perform();

    }

    public void scrollToLastParagraph(){
        WebDriver webDriver = DriverFactory.getDriver();
        WebElement lastParagraph = webDriver.findElement(By.cssSelector("p:last-of-type"));
        wait.until(ExpectedConditions.visibilityOf(lastParagraph));
        //actions.scrollToElement(driver.findElement(lastParagraph)).perform();
        JavaScriptUtils.scrollToElementJS(webDriver, lastParagraph);
    }


}
