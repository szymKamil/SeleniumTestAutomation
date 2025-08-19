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

public class DropdownMenu extends AbstractPage {


    Actions actions;

    public DropdownMenu(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(id = "my-dropdown-1")
    public WebElement dropdownOneLeftClick;

    @FindBy(id = "my-dropdown-2")
    public WebElement dropdownTwoRightClick;

    @FindBy(id = "my-dropdown-3")
    public WebElement dropdownThreeDoubleClick;

    @FindBy(css = "ul.dropdown-menu:not([style*='none'])")
    public WebElement dropdownMenuVisible;


    //Metody testowe
    public void openDropdownOne(){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownOneLeftClick)).click();
    }

    public void openDropdownTwo(){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownTwoRightClick));
        actions.contextClick(dropdownTwoRightClick).perform();
    }

    public void openDropdownThree(){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownOneLeftClick));
        actions.doubleClick(dropdownThreeDoubleClick).perform();
    }

    public void pickDropdownElement(int index){
        wait.until(ExpectedConditions.elementToBeClickable(dropdownMenuVisible));
        dropdownMenuVisible.findElement(By.xpath(".//li[ " + index + " ]/a[@class='dropdown-item']")).click();
        wait.until(ExpectedConditions.invisibilityOf(dropdownMenuVisible));
    }

}
