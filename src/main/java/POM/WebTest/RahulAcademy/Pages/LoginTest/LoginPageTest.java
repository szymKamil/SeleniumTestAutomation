package POM.WebTest.RahulAcademy.Pages.LoginTest;

import Base.Utils.CredentialsAES;

import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;


public class LoginPageTest  {

    WebDriver driver;
    WebDriverWait wait;
    Logger log;
    WebElementActions actions;

    public LoginPageTest(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        WebElementActions.setDriver(driver, wait);
        actions = new WebElementActions();
        PageFactory.initElements(driver, this);
    }

    //Dane
    private static final String login = "zZf2rJMkK2K7X70NR/nbIA==";
    private static final String password = "ZVxOYnCTQLgCBPYuk9Im8vLs/2ePFUdif9/7Oh7xYas=";


    //Elementy strony

    @FindBy(css = "form.form")
    public WebElement formBody;

    public By formBdy = By.cssSelector("form.form");

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

    By checkboxAgreeTerms = By.id("chkboxTwo");


    @FindBy(css = "div.forgot-pwd-container")
    public WebElement btnForgotPass;

    @FindBy(css = "button.submit")
    public WebElement btnSubmit;

    public By submitBtn = By.cssSelector("button.submit");

    @FindBy(css = "div.overlay-right")
    public WebElement rightPanel;

    @FindBy(id = "visitUsTwo")
    public WebElement visitUsBtn;

    @FindBy(css = "div.login-container ")
    public WebElement successfullyLoginDiv;

    @FindBy(css = "div.login-container strong")
    public WebElement rahulShettyStrongName;

    By rahulShettyStrong = By.cssSelector("div.login-container strong");

    @FindBy(css = "button.logout-btn")
    public WebElement logoutButton;

    By logoutBtn = By.cssSelector("button.logout-btn");

    @FindBy(css = "p.error")
    public WebElement errorLoginMsg;

    public By errorLogMsg = By.cssSelector("p.error");


    By successInfo = By.xpath("//*[@id=\"root\"]/div/div/div/h2");


    //DSL
    public void enterUsername(String username){
        actions.find(usernameIn).enterText(username);
    }

    public void enterPassword(String password){
        actions.find(passwordIn).enterText(password);
    }

    //Metody testowe
    public void insertLogInTo(String login, String password)  {
        enterUsername(login);
        enterPassword(password);
    }

    public boolean verifyElementVisibility(By element){
       return actions.find(element).isVisible();
    }

    public void selectTermsAndConditions(){
        actions.find(checkboxAgreeTerms).click();
    }

    public boolean verifyTermsAreSelected(){
        return actions.find(checkboxAgreeTerms).isSelected();
    }

    public void insertLogInTo() throws Exception {
        insertLogInTo(CredentialsAES.decrypt(login), CredentialsAES.decrypt(password));
    }

    public void clickSignIn()  {
        actions.find(submitBtn).click();
    }

    public boolean verifyElementText(By element, String textToCompare){
        return actions.find(element).getText().equals(textToCompare);
    }

    public String getElementText(By element){
        return actions.find(element).getText();
    }


    public void verifySuccessfulLogin()  {
       String successInfoText =  actions.find(successInfo).getText();
       log.info("Komunikat po zalogowaniu brzmi: {}", successInfoText);
       String colorInfo = actions.find(rahulShettyStrong).getCss("color");
       log.info("Kolor elementu ma następującą wartość: {}", colorInfo);
       if(!colorInfo.equals("rgba(255, 75, 43, 1)")){
           log.error("Błędna kolorystyka elementu, ma ona wartość: {}", colorInfo);
       }
       actions.find(logoutBtn).click();
       actions.find(formBdy).isVisible();
    }

    public void visitUsClick()  {
        wait.until(ExpectedConditions.elementToBeClickable(visitUsBtn)).click();
    }


}
