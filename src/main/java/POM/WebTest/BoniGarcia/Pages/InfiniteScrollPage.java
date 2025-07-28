package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class InfiniteScrollPage {

    private WebDriver driver;
    private WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    public InfiniteScrollPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public Long returnPageHeight(){
        return (Long) js.executeScript("return document.body.scrollHeight");

    }

    public void scrollXTimes(int numOfScrollsDown){
        for (int i = 0; i < numOfScrollsDown; i++){
            int numberOfParagraphs = driver.findElements(By.tagName("p"))
                    .size();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("p"), numberOfParagraphs));
        }
    }


    public void scrollXTimes(){
        scrollXTimes(3);
    }


}
