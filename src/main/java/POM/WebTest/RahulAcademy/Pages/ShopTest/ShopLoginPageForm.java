package POM.WebTest.RahulAcademy.Pages.ShopTest;

import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;



public class ShopLoginPageForm  {


    Logger log;
    WebDriver driver;
    WebDriverWait wait;
    WebElementActions actions;


    public ShopLoginPageForm(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        actions = new WebElementActions(driver, wait);
    }

    //Elementy
    By usernameInput = By.id("username");
    By passwordInput = By.id("password");
    By radioAdmin = By.xpath("//input[@id='usertype' and @value='admin']/following-sibling::span");
    By radioUser = By.xpath("//input[@id='usertype' and @value='user']/following-sibling::span");
    By termsCheckbox = By.cssSelector("input#terms");
    By signInBtn = By.id("signInBtn");
    By selectList = By.cssSelector("select.form-control");
    By loginInfo = By.cssSelector("p b:first-child i");
    By passwordInfo = By.cssSelector("p b:last-child i");


    public String getLogin(){
        return actions.find(loginInfo).getText();
    }
    public String getPassword(){
        return actions.find(passwordInfo).getText();
    }

    public ShopLoginPageForm insertLogin(){
        actions.find(usernameInput).enterText(getLogin());
        return this;
    }

    public ShopLoginPageForm insertPassword(){
        actions.find(passwordInput).enterText(getPassword());
        return this;
    }

    public ShopLoginPageForm choseRadio(String userType){
        switch (userType.toLowerCase()) {
            case "admin" -> actions.find(radioAdmin).click().isSelected();
            case "user" -> actions.find(radioUser).click().isSelected();
            default -> log.error("Błędnie wybrana opcja radiobutton");
        }
        return this;
    }

    public ShopLoginPageForm selectForms(String text){
        actions.selectOptionByText(selectList, text);
        return this;
    }

    public ShopLoginPageForm selectTermsAndConditions(){
        actions.find(termsCheckbox).click().isSelected();
        return this;
    }

    public ShopPage clickSignIn(){
        actions.find(signInBtn).click();
        return new ShopPage(driver, wait, log);
    }



}
