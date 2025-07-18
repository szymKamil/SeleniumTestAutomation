package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.List;

public class LongPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private Actions actions;


    public LongPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    private By lead = By.cssSelector("p.lead");


    public String getTextFromParagraph(int i){
        return driver.findElements(lead).get(i).getText();

    }

    public void printAllParagraphs(){
        List<WebElement> paragraphs = driver.findElements(lead);
        for (int i = 0; i < paragraphs.size(); i++){
            String paragraphText = getTextFromParagraph(i);
            log.info("Paragraf numer {} posiada tekst: {}", (i+1), paragraphText);
        }
    }

    public void scrollToParagraph(int number){
        List<WebElement> paragraphs = driver.findElements(lead);
        actions.scrollToElement(paragraphs.get(number)).perform();

    }

    public void scrollToLastParagraph(){
        WebElement lastParagraph = driver.findElement(By.cssSelector("p:last-of-type"));
        actions.scrollToElement(lastParagraph).perform();

    }


}
