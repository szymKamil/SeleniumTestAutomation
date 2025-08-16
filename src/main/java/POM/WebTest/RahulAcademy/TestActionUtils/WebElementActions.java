package POM.WebTest.RahulAcademy.TestActionUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementActions {

    static public WebDriver driver;
    static WebDriverWait wait;
    WebElement element;
    Select select;

    public WebElementActions() {
    }

    public WebElementActions(WebElement element) {
        this.element = element;
    }

    public static void setDriver(WebDriver driver, WebDriverWait wait){
        WebElementActions.driver = driver;
        WebElementActions.wait = wait;
    }

    public WebElementActions find(By locator){
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return new WebElementActions(el);
    }


     public WebElement enterText(String text){
        element.clear();
        element.sendKeys(text);
        return element;
    }

    public String getText(){
        return element.getText();
    }


    public WebElement click(){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        return element;
    }

    public String getAttribute(String attribute){
        return element.getAttribute(attribute);
    }

    public String getCss(String attribute){
        return element.getCssValue(attribute);
    }

    public Boolean isVisible(){
        return element.isDisplayed();
    }

    public Boolean isSelected(){
        return element.isSelected();
    }



    public WebElementActions selectOptionByText(By element, String text){
        select = new Select(driver.findElement(element));
        select.selectByVisibleText(text);
        return this;

    }

}
