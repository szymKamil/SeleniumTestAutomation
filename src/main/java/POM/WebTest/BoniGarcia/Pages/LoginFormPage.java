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

    private final static String LOGIN = "user";
    private final static String PASSWORD = "user";
    private final static String LOGGED_SUCESS_URL = "https://bonigarcia.dev/selenium-webdriver-java/login-sucess.html";
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
    WebElement INPUT_USERNAME;

    @FindBy(id = "password")
    WebElement INPUT_PASSWORD;

    @FindBy(css = "button.btn")
    WebElement SUBMIT_BTN;

    @FindBy(id = "success")
    WebElement LOGIN_SUCESS_DIV;

    @FindBy(id = "spinner")
    WebElement SPINNER_ICON;


    //Metody testowe
    public void logIn(){
        wait.until(ExpectedConditions.elementToBeClickable(INPUT_USERNAME)).sendKeys(LOGIN);
        wait.until(ExpectedConditions.elementToBeClickable(INPUT_PASSWORD)).sendKeys(PASSWORD);
        assertThat(INPUT_USERNAME.getDomProperty("value")).isEqualTo(LOGIN);
        assertThat(INPUT_PASSWORD.getDomProperty("value")).isEqualTo(PASSWORD);
    }

    public void clickSubmitBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BTN)).click();
        fluentWait.until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOf(SPINNER_ICON),
                ExpectedConditions.numberOfElementsToBe(By.id("spinner"), 0)
        ));

    }
    public void verifySuccessLogin(){
        wait.until(PageLoadedVerification.pageIsLoaded());
        assertThat(driver.getCurrentUrl()).contains(LOGGED_SUCESS_URL);
        assertThat(LOGIN_SUCESS_DIV.getText()).isEqualTo("Login successful");
    }

}
