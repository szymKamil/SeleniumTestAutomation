package POM.WebTest.RahulAcademy.Pages.ShopTest;

import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class ShopPage  {

    Logger log;
    WebDriver driver;
    WebDriverWait wait;
    WebElementActions actions;
    ShopLoginPageForm loginForm;

    public ShopPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        actions = new WebElementActions(driver, wait);
        loginForm = new ShopLoginPageForm(driver, wait, log);


    }


    //Elementy
    By appShop = By.xpath("//app-shop");
    By carouselSlider = By.id("carouselExampleIndicators");
    By carouselSliderNext = By.cssSelector("a.carousel-control-next");
    By carouselSliderList = By.cssSelector("ol.carousel-indicators");



    public ShopPage logInToAppAs(String userType, String userRole){
        loginForm.insertLogin().insertPassword().choseRadio(userType).selectForms(userRole).selectTermsAndConditions().clickSignIn();
        return this;
    }

    public ShopPage verifyShopIsOnline(){
        actions.find(appShop);
        return this;
    }

    public ShopPage verifySlider(){
    actions.find(carouselSlider).isVisible();
    String className;
    do{
         actions.find(carouselSliderNext).click();
         className = actions.findAll(carouselSliderNext).getFirst().classGetter();
     } while (className.equals("active"));
     return this;

    }


}
