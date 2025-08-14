package POM.WebTest.RahulAcademy.Pages;

import Base.Utils.CredentialsAES;
import POM.WebTest.RahulAcademy.Utils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;


import static org.assertj.core.api.Assertions.assertThat;


public class LoginPageTest  {

    WebDriver driver;
    WebDriverWait wait;
    Logger log;
    WebElementActions actions;

    public LoginPageTest(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        WebElementActions.setDriver(driver);
        actions = new WebElementActions();
        PageFactory.initElements(driver, this);
    }

    //Dane
    private static final String login = "zZf2rJMkK2K7X70NR/nbIA==";
    private static final String password = "ZVxOYnCTQLgCBPYuk9Im8vLs/2ePFUdif9/7Oh7xYas=";


    //Elementy strony

    @FindBy(css = "form.form")
    public WebElement formBody;

    @FindBy(css = "form.form h1")
    public WebElement signInHeader;

    @FindBy(id = "inputUsername")
    public WebElement usernameInput;

    By usernameIn = By.id("inputUsername");

    @FindBy(name = "inputPassword")
    public WebElement passwordInput;

    By passwordIn = By.name("inputPassword");

    @FindBy(id = "chkboxOne")
    public WebElement checkboxRememberUserName;

    @FindBy(id = "chkboxTwo")
    public WebElement checkboxAgreeTermsAndPolicy;

    @FindBy(css = "div.forgot-pwd-container")
    public WebElement btnForgotPass;

    @FindBy(css = "button.submit")
    public WebElement btnSubmit;

    By submitBtn = By.cssSelector("button.submit");

    @FindBy(css = "div.overlay-right")
    public WebElement rightPanel;

    @FindBy(id = "visitUsTwo")
    public WebElement visitUsBtn;

    @FindBy(css = "div.login-container ")
    public WebElement successfullyLoginDiv;

    @FindBy(css = "div.login-container strong")
    public WebElement rahulShettyStrongName;

    @FindBy(css = "button.logout-btn")
    public WebElement logoutButton;

    @FindBy(css = "p.error")
    public WebElement errorLoginMsg;



    //DSL
    public void enterUsername(String username){
        actions.find(usernameIn).enterText(username);
    }

    public void enterPassword(String password){
        actions.find(passwordIn).enterText(password);
    }


    //Metody testowe
    public void insertLogInTo(String login, String password) throws Exception {
        actions.find(usernameIn).enterText(login);
        actions.find(passwordIn).enterText(password);
//        assertThat(actions.find(usernameIn).getAttribute("value").contains(login)).isTrue();
//        assertThat(actions.find(passwordIn).getAttribute("value").contains(password)).isTrue();

    }

    public void insertLogInTo() throws Exception {
        insertLogInTo(CredentialsAES.decrypt(login), CredentialsAES.decrypt(password));
    }

    public void clickSignIn()  {
        actions.find(submitBtn).click();
    }


    public void verifySuccessfulLogin() throws Exception {
       String successInfo =  wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"root\"]/div/div/div/h2\n")))).getText();
       log.info("Komunikat po zalogowaniu brzmi: {}", successInfo);
       String colorInfo = wait.until(ExpectedConditions.visibilityOf(rahulShettyStrongName))
               .getCssValue("color");
       log.info("Kolor elementu ma następującą wartość: {}", colorInfo);
       if(!colorInfo.equals("rgba(255, 75, 43, 1)")){
           log.error("Błędna kolorystyka elementu, ma ona wartość: {}", colorInfo);
       }
       wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
       wait.until(ExpectedConditions.visibilityOf(formBody)).isDisplayed();
    }

    public void visitUsClick()  {
        wait.until(ExpectedConditions.elementToBeClickable(visitUsBtn)).click();
    }






}
