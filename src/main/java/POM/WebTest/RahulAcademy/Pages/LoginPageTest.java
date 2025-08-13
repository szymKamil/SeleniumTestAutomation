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


public class LoginPageTest extends WebElementActions {

    WebDriver driver;
    WebDriverWait wait;
    Logger log;

    public LoginPageTest(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);

    }

    //Dane
    private static final String login = "vd5J1jErnMAcL1kq40bbuA==";
    private static final String password = "lyH/5+2uFKOVGAzTmbwZeJxiUTTymRWhYOK8RZzfNbs=";


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

    By passwordIn = By.id("inputPassword");

    @FindBy(id = "chkboxOne")
    public WebElement checkboxRememberUserName;

    @FindBy(id = "chkboxTwo")
    public WebElement checkboxAgreeTermsAndPolicy;

    @FindBy(css = "div.forgot-pwd-container")
    public WebElement btnForgotPass;

    @FindBy(css = "button.submit")
    public WebElement btnSubmit;

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


    //Metody testowe
    public void insertLogInTo(String login, String password) throws Exception {
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(login);
        assertThat(usernameInput.getAttribute("value").contains(login)).isTrue();
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
        assertThat(passwordInput.getAttribute("value").contains(password)).isTrue();
    }

    public void insertLogInTo() throws Exception {
        insertLogInTo(CredentialsAES.decrypt(login), CredentialsAES.decrypt(password));
    }

    public void clickSignIn()  {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit)).click();
    }


    public void verifySuccessfulLogin() throws Exception {
       String successInfo =  wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"root\"]/div/div/div/h2\n")))).getText();
       log.info("Komunikat po zalogowaniu brzmi: {}", successInfo);
       assertThat(successInfo.contains(CredentialsAES.decrypt(login))).isTrue();
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

    //DSL
    public void enterUsername(String username){
        find(usernameIn).enterText(username);
    }

    public void enterPassword(String password){
        find(passwordIn).enterText(password);
    }




}
