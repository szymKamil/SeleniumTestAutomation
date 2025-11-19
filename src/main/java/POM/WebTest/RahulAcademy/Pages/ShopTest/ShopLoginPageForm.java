package POM.WebTest.RahulAcademy.Pages.ShopTest;

import Base.Drivers.DriverFactory;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShopLoginPageForm  {


    Logger log;
    WebDriver driver;
    WebDriverWait wait;
    WebElementActions actions;


    public ShopLoginPageForm(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new WebElementActions(DriverFactory.getDriver(), DriverFactory.getWait());
        log = LoggerFactory.getLogger("Logger");
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
    By modalBody = By.cssSelector("div.modal-body");
    By modalBodyOKBtn = By.id("okayBtn");


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
            case "user" -> {
                actions.find(radioUser)
                        .click()
                        .isSelected();
                handleAlert();
            }
            default -> log.error("Błędnie wybrana opcja radiobutton");
        }
        return this;
    }


    public void handleAlert(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalBody));
        wait.until(ExpectedConditions.elementToBeClickable(modalBodyOKBtn)).click();
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
        return new ShopPage(DriverFactory.getDriver(), DriverFactory.getWait());
    }



}
