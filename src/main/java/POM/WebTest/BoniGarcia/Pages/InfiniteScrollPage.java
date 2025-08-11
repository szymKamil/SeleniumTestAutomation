package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public Long getPageHeight(){
        return (Long) js.executeScript("return document.body.scrollHeight");
    }

    public void scrollXTimes(int numOfScrollsToPerform){
        for (int i = 0; i < numOfScrollsToPerform; i++){
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
