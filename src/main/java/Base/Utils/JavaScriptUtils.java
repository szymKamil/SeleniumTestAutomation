package Base.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class JavaScriptUtils extends Utils{

    public static void scrollToElementJS(WebElement element){
        String jsScroll = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor) driver).executeScript(jsScroll, element);
    }

    public static void scrollToElementByJS(By locator){
        WebElement element = driver.findElement(locator);
        String jsScroll = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor) driver).executeScript(jsScroll, element);
    }

}
