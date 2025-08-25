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

public class DropdownMenuPage extends AbstractPage {


    Actions actions;

    public DropdownMenuPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(id = "my-dropdown-1")
    public WebElement DROPDOWN_BTN_LEFT_CLICK;

    @FindBy(id = "my-dropdown-2")
    public WebElement DROPDOWN_BTN_CONTEXT_CLICK;

    @FindBy(id = "my-dropdown-3")
    public WebElement DROPDOWN_BTN_DOUBLE_CLICK;

    @FindBy(css = "ul.dropdown-menu:not([style*='none'])")
    public WebElement DROPDOWN_MENU_VISIBLE;


    //Metody testowe
    public void openDropOneAndPick(int index){
        wait.until(ExpectedConditions.elementToBeClickable(DROPDOWN_BTN_LEFT_CLICK)).click();
        pickDropdownElement(index);
    }

    public void openDropTwoAndPick(int index){
        wait.until(ExpectedConditions.elementToBeClickable(DROPDOWN_BTN_CONTEXT_CLICK));
        actions.contextClick(DROPDOWN_BTN_CONTEXT_CLICK).perform();
        pickDropdownElement(index);
    }

    public void openDropThreeAndPick(int index){
        wait.until(ExpectedConditions.elementToBeClickable(DROPDOWN_BTN_LEFT_CLICK));
        actions.doubleClick(DROPDOWN_BTN_DOUBLE_CLICK).perform();
        pickDropdownElement(index);
    }

    public void pickDropdownElement(int index){
        wait.until(ExpectedConditions.elementToBeClickable(DROPDOWN_MENU_VISIBLE));
        DROPDOWN_MENU_VISIBLE.findElement(By.xpath(".//li[ " + index + " ]/a[@class='dropdown-item']")).click();
        wait.until(ExpectedConditions.invisibilityOf(DROPDOWN_MENU_VISIBLE));
    }

}
