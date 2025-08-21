package POM.WebTest.BoniGarcia.Pages;

import Base.Utils.PageLoadedVerification;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginFormPage extends AbstractPage{

    private final static String login = "user";
    private final static String password = "user";
    private final static String loggedURL = "https://bonigarcia.dev/selenium-webdriver-java/login-sucess.html";
    FluentWait<WebDriver> fluentWait;


    public LoginFormPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
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
    WebElement loginSuccessDiv;

    @FindBy(id = "spinner")
    WebElement spinnerIcon;


    //Metody testowe
    public void logIn(){
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername)).sendKeys(login);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).sendKeys(password);
        assertThat(inputUsername.getDomProperty("value")).isEqualTo(login);
        assertThat(inputPassword.getDomProperty("value")).isEqualTo(password);
    }

    public void clickSubmitBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        fluentWait.until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOf(spinnerIcon),
                ExpectedConditions.numberOfElementsToBe(By.id("spinner"), 0)
        ));

    }

    public void verifySuccessLogin(){
        wait.until(PageLoadedVerification.pageIsLoaded());
        assertThat(driver.getCurrentUrl()).contains(loggedURL);
        assertThat(loginSuccessDiv.getText()).isEqualTo("Login successful");
    }

}
