package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.List;

public class LongPage extends AbstractPage{


    private Actions actions;


    public LongPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        actions = new Actions(this.driver);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(css = "p.lead")
    List<WebElement> leads;

    private By lead = By.cssSelector("p.lead");

    //Metody testowe
    public String getTextFromParagraph(int i){
        return wait.until(ExpectedConditions.visibilityOfAllElements(leads)).get(i).getText();

    }

    public void printAllParagraphs(){
        for (int i = 0; i < leads.size(); i++){
            String paragraphText = getTextFromParagraph(i);
            log.info("Paragraf numer {} posiada tekst: {}", (i+1), paragraphText);
        }
    }

    public void scrollToParagraph(int number){
       actions.scrollToElement(leads.get(number)).perform();

    }

    public void scrollToLastParagraph(){
        WebElement lastParagraph = driver.findElement(By.cssSelector("p:last-of-type"));
        actions.scrollToElement(lastParagraph).perform();

    }


}
