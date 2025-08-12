package POM.WebTest.RahulAcademy.Pages;

import Base.Utils.CredentialsAES;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;


import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginPageTest {

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

    @FindBy(name = "inputPassword")
    public WebElement passwordInput;

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

    public void insertLogInTo() throws Exception {
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(CredentialsAES.decrypt(login));
        assertThat(usernameInput.getAttribute("value").contains(CredentialsAES.decrypt(login))).isTrue();
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(CredentialsAES.decrypt(password));
        assertThat(passwordInput.getAttribute("value").contains(CredentialsAES.decrypt(password))).isTrue();
    }

    public void clickSignIn()  {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit)).click();
    }

    public void verifySuccessfulLogin() throws Exception {
       String successInfo =  wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("div.login-container h1")))).getText();
       log.info("Komunikat po zalogowaniu brzmi: {}", successInfo);
       assertThat(successInfo.contains(CredentialsAES.decrypt(login))).isTrue();
       String colorInfo = wait.until(ExpectedConditions.visibilityOf(rahulShettyStrongName))
               .getCssValue("color");
       log.info("Kolor elementu ma następującą wartość: {}", colorInfo);
       wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();

    }


}
