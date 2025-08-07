package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.PageLoadedVerification;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import static org.assertj.core.api.Assertions.assertThat;


public class LoginFormPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;
    private final static String login = "user";
    private final static String password = "user";
    private final static String loggedURL = "https://bonigarcia.dev/selenium-webdriver-java/login-sucess.html?username=user&password=user";


    public LoginFormPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "username")
    WebElement inputUsername;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(css = "button.btn")
    WebElement submitBtn;

    @FindBy(id = "success")
    WebElement loginSuccessDiv;


    public void logIn(){
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername)).sendKeys(login);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).sendKeys(password);
        assertThat(inputUsername.getDomProperty("value")).isEqualTo(login);
        assertThat(inputPassword.getDomProperty("value")).isEqualTo(password);
    }

    public void clickSubmitBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
    }

    public void verifySuccessLogin(){
        wait.until(PageLoadedVerification.pageIsLoaded());
        assertThat(driver.getCurrentUrl()).isEqualTo(loggedURL);
        assertThat(loginSuccessDiv.getText()).isEqualTo("Login successful");

    }


}
