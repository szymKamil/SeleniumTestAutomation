package POM.WebTest.RahulAcademy.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebElementActions {

    public static WebDriver driver;
    WebElement element;

    public WebElementActions(WebElement element) {
        this.element = element;
    }

    public void setDriver(WebDriver driver){
        WebElementActions.driver = driver;
    }

    public WebElementActions find(By locator){
        return new WebElementActions(driver.findElement(locator));
    }

    public WebElement enterText(String text){
        element.clear();
        element.sendKeys(text);
        return element;
    }

    public void click(By locator){
        element.click();
    }

}
