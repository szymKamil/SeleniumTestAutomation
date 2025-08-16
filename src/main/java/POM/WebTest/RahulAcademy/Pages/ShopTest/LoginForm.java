package POM.WebTest.RahulAcademy.Pages.ShopTest;

import POM.WebTest.RahulAcademy.Pages.SignInFormTest.ShopPage;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class LoginForm extends WebElementActions {


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginForm.class);
    WebDriver driver;
    WebDriverWait wait;
    Logger logger;
    WebElementActions actions;


    public LoginForm(Logger logger, WebDriverWait wait, WebDriver driver) {
        this.logger = logger;
        this.wait = wait;
        this.driver = driver;
        WebElementActions.setDriver(driver, wait);
        actions = new WebElementActions();

    }

    //Elementy
    By usernameInput = By.id("username");
    By passwordInput = By.id("password");
    By radioAdmin = By.xpath("//input[@id='usertype' and @value='admin']");
    By radioUser = By.xpath("//input[@id='usertype' and @value='user']");
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

    public LoginForm insertLogin(){
        actions.find(usernameInput).enterText(getLogin());
        return this;
    }

    public LoginForm insertPassword(){
        actions.find(passwordInput).enterText(getPassword());
        return this;
    }

    public LoginForm choseRadio(String userType){
        switch (userType.toLowerCase()) {
            case "admin" -> find(radioAdmin).click().isSelected();
            case "user" -> find(radioUser).click().isSelected();
            default -> log.error("Błędnie wybrana opcja radiobutton");
        }
        return this;
    }

    public LoginForm selectForms(String text){
        actions.selectOptionByText(selectList, text);
        return this;
    }

    public LoginForm selectTermsAndConditions(){
        find(termsCheckbox).click().isSelected();
        return this;
    }

    public ShopPage clickSignIn(){
        find(signInBtn).click();
        return new ShopPage();
    }



}
