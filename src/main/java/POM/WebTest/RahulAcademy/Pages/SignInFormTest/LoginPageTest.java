package POM.WebTest.RahulAcademy.Pages.SignInFormTest;

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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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
    public LoginPageTest enterUsername(String username){
        actions.find(usernameIn).enterText(username);
        return this;
    }

    public LoginPageTest enterPassword(String password){
        actions.find(passwordIn).enterText(password);
        return this;
    }

    //Metody testowe
    public LoginPageTest logInTo(String login, String password)  {
        enterUsername(login).enterPassword(password);
        return this;
    }

    public LoginPageTest logInTo() throws Exception {
        logInTo(CredentialsAES.decrypt(login), CredentialsAES.decrypt(password));
        return this;
    }

    public LoginPageTest elementShouldBeVisible(By element){
        assertThat(actions.find(element).isVisible()).isTrue();
        return this;
    }

    public LoginPageTest selectTermsAndConditions(){
        actions.find(checkboxAgreeTerms).click();
        return this;
    }

    public LoginPageTest verifyTermsAreSelected(){
        if (!actions.find(checkboxAgreeTerms).isSelected()) {
            actions.find(checkboxAgreeTerms).isSelected();
        }
        return this;
    }

    public LoginPageTest clickSignIn()  {
        actions.find(submitBtn).click();
        return this;
    }
    public LoginPageTest verifySuccessLoginInfo()  {
        String successInfoText =  actions.find(successInfo).getText();
        log.info("Komunikat po zalogowaniu brzmi: {}", successInfoText);
        return this;
    }


    public LoginPageTest shouldSeeErrorLoginMsg()  {
        actions.find(errorLogMsg).getText().equals("* Incorrect username or password");
        return this;
    }


    public LoginPageTest shouldHaveSuccessMessageColor(){
         assertThat(actions.find(rahulShettyStrong).getCss("color").contains("255, 75, 43, 1")).isTrue();
         return this;
    }

    public LoginPageTest verifyElementText(By element, String textToCompare){
        log.info("Weryfikacja tekstu z elementu {} ze spodziewanym tekstem: {}", actions.find(element).getText(), textToCompare);
        assertThat(actions.find(element).getText().equals(textToCompare)).isTrue();
        return this;
    }

    public LoginPageTest visitUsClick()  {
        wait.until(ExpectedConditions.elementToBeClickable(visitUsBtn)).click();
        return this;
    }

    public LoginPageTest logoutVerification()  {
        actions.find(logoutBtn).click();
        actions.find(formBdy).isVisible();
        return this;
    }

    public LoginPageTest useBtnVisitUs()  {
        String currentHandle = driver.getWindowHandle();
        visitUsClick();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        List<String> windowHandels = driver.getWindowHandles().stream().toList();
        if(windowHandels.size() == 2 && driver.getWindowHandle().equals(currentHandle)){
            driver.switchTo().window(windowHandels.get(1));
        }
        String url = driver.getCurrentUrl();
        assert url != null;
        assertThat(url.contains("https://rahulshettyacademy.com/")).isTrue();
        log.info("URL po przejściu do Visit Us to: {}", url);

        return this;
    }


}
