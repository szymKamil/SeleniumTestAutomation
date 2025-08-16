package POM.WebTest.RahulAcademy.Pages.ShopTest;

import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class ShopPage extends WebElementActions {

    Logger log;
    WebDriver driver;
    WebDriverWait wait;
    WebElementActions actions;
    ShopLoginPageForm loginForm;

    public ShopPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        WebElementActions.setDriver(driver, wait);
        actions = new WebElementActions();
        loginForm = new ShopLoginPageForm(driver, wait, log);

    }



    //Elementy
    By appShop = By.xpath("//app-shop");

    public ShopPage logInToAppAs(String userType, String userRole){
        loginForm.insertLogin().insertPassword().choseRadio(userType).selectForms(userRole).selectTermsAndConditions().clickSignIn();
        return this;
    }

    public ShopPage verifyShopIsOnline(){
        find(appShop);
        return this;
    }


}
