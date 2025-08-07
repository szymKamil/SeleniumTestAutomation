package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.PageLoadedVerification;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginFormPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;
    private final static String login = "user";
    private final static String password = "user";
    private final static String loggedURL = "https://bonigarcia.dev/selenium-webdriver-java/login-sucess.html";
    FluentWait<WebDriver> fluentWait;


    public LoginFormPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        fluentWait = new FluentWait<>(driver).pollingEvery(Duration.ofSeconds(1)).withTimeout(Duration.ofSeconds(3));
    }

    @FindBy(id = "username")
    WebElement inputUsername;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(css = "button.btn")
    WebElement submitBtn;

    @FindBy(id = "success")
    WebElement loginSuccessDiv;

    @FindBy(id = "spinner")
    WebElement spinnerIcon;


    public void logIn(){
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername)).sendKeys(login);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).sendKeys(password);
        assertThat(inputUsername.getDomProperty("value")).isEqualTo(login);
        assertThat(inputPassword.getDomProperty("value")).isEqualTo(password);
    }

    public void clickSubmitBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        fluentWait.until(ExpectedConditions.visibilityOf(spinnerIcon));
        fluentWait.until(ExpectedConditions.invisibilityOf(spinnerIcon));
    }

    public void verifySuccessLogin(){
        wait.until(PageLoadedVerification.pageIsLoaded());
        assertThat(driver.getCurrentUrl()).contains(loggedURL);
        assertThat(loginSuccessDiv.getText()).isEqualTo("Login successful");
    }

}
