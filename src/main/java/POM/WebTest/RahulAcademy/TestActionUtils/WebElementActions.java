package POM.WebTest.RahulAcademy.TestActionUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class WebElementActions {

    public WebDriver driver;
     WebDriverWait wait;
    Select select;
    WebElement element;
    List<WebElementActions> elements;

    // Główna instancja, która przechowuje driver i wait
    public WebElementActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Konstruktor dla pojedynczego elementu — bierze driver i wait z głównej instancji
    public WebElementActions(WebElement element, WebElementActions mainActions) {
        this.element = element;
        this.driver = mainActions.driver;
        this.wait = mainActions.wait;
    }

    // Konstruktor dla listy elementów
    public WebElementActions(List<WebElementActions> elements) {
        this.elements = elements;
    }


    public WebElementActions find(By locator){
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return new WebElementActions(el, this);
    }

    public List<WebElementActions> findAll(By locator){
        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        return webElements.stream()
                .map(el -> new WebElementActions(el, this))
                .toList();
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

    public String classGetter(){
        return String.valueOf(element.getClass());
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
