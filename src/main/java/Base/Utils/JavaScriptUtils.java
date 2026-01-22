package Base.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class JavaScriptUtils extends Utils{

    /**
     * Utils pozwalający na scroll za pomocą JS do wskazanego elementu.
     * @param driver przekazujemy uruchomiony driver.
     * @param element przekazujemy element w formie WebElementu.
     */
    public static void scrollToElementJS(WebDriver driver, WebElement element){
        String jsScroll = "arguments[0].scrollIntoView(false);";
        ((JavascriptExecutor) driver).executeScript(jsScroll, element);
    }

    /**
     * Utils pozwalający na scroll za pomocą JS do wskazanego lokatora.
     * @param driver przekazujemy uruchomiony driver.
     * @param locator przekazujemy element w formie lokatora (By).
     */
    public static void scrollToElementByJS(WebDriver driver, By locator){
        WebElement element = driver.findElement(locator);
        String jsScroll = "arguments[0].scrollIntoView(false);";
        ((JavascriptExecutor) driver).executeScript(jsScroll, element);
    }

}
