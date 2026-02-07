package POM.WebTest.BoniGarcia.Pages;

import Base.Utils.PageLoadedVerification;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginFormPage extends AbstractPage{

    private final static String LOGIN = "user";
    private final static String PASSWORD = "user";
    private final static String LOGGED_SUCESS_URL = "https://bonigarcia.dev/selenium-webdriver-java/login-sucess.html";
    FluentWait<WebDriver> fluentWait;


    public LoginFormPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(this.driver, this);
        Collection<Class<? extends RuntimeException>> errorsCollection = new ArrayList<>(List.of(NoSuchElementException.class, TimeoutException.class));
        fluentWait = new FluentWait<>(this.driver).pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(3))
                .ignoreAll(errorsCollection);
    }

    //Elementy na stronie
    @FindBy(id = "username")
    WebElement inputUsername;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(css = "button.btn")
    WebElement submitBtn;

    @FindBy(id = "success")
    WebElement loginSucessDiv;

    @FindBy(id = "spinner")
    WebElement spinnerIcon;


    //Metody testowe
    public void logIn(){
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername)).sendKeys(LOGIN);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).sendKeys(PASSWORD);
        assertThat(inputUsername.getDomProperty("value")).isEqualTo(LOGIN);
        assertThat(inputPassword.getDomProperty("value")).isEqualTo(PASSWORD);
    }

    public void clickSubmitBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        fluentWait.until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOf(spinnerIcon),
                ExpectedConditions.numberOfElementsToBe(By.id("spinner"), 0)
        ));

    }
    public void verifySuccessLogin(){
        wait.until(ExpectedConditions.urlContains(LOGGED_SUCESS_URL));
        assertThat(driver.getCurrentUrl()).contains(LOGGED_SUCESS_URL);
        assertThat(loginSucessDiv.getText()).isEqualTo("Login successful");
    }

}
