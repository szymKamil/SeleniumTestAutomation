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


    public WebElementActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    public WebElementActions(WebElement element, WebElementActions instance) {
        this.element = element;
        this.driver = instance.driver;
        this.wait = instance.wait;
    }


    public WebElementActions(List<WebElementActions> elements, WebElementActions instance) {
        this.elements = elements;
        this.driver = instance.driver;
        this.wait = instance.wait;
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

    public WebElementActions pickElementByText(List<WebElementActions> elements, String text){
        return elements.stream().filter(e -> element.getText().contains(text)).findFirst().orElseThrow();
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
