package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DropdownMenuPage extends AbstractPage {


    Actions actions;

    public DropdownMenuPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(id = "my-dropdown-1")
    public WebElement dropdownBtnLeftClick;

    @FindBy(id = "my-dropdown-2")
    public WebElement dropdownBtnContextClick;

    @FindBy(id = "my-dropdown-3")
    public WebElement dropdownBtnDoubleClick;

    @FindBy(css = "ul.dropdown-menu:not([style*='none'])")
    public WebElement dropdownMenuVisible;


    //Metody testowe
    public void openDropOneAndPick(int index){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownBtnLeftClick)).click();
        pickDropdownElement(index);
    }

    public void openDropTwoAndPick(int index){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownBtnContextClick));
        actions.contextClick(dropdownBtnContextClick).perform();
        pickDropdownElement(index);
    }

    public void openDropThreeAndPick(int index){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownBtnLeftClick));
        actions.doubleClick(dropdownBtnDoubleClick).perform();
        pickDropdownElement(index);
    }

    public void pickDropdownElement(int index){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownMenuVisible));
        dropdownMenuVisible.findElement(By.xpath(".//li[ " + index + " ]/a[@class='dropdown-item']")).click();
        wait.until(ExpectedConditions.invisibilityOf(dropdownMenuVisible));
    }

}
