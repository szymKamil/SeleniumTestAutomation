package POM.WebTest.SwagLabs.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.TestNG;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;
    Logger log;

    public LoginPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
    }

    //Lokatory
    @FindBy(id = "user-name")
    WebElement usernameInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "login-button")
    WebElement loginBtn;

    @FindBy(id = "login_credentials")
    WebElement loginCredentials;

    @FindBy(css = "div.login_password")
    WebElement passwordCredentials;


    public String getLogin(){
        var usenameList = Arrays.stream(loginCredentials.getText().split("\n")).filter(e -> e.contains("user")).toList();
        Random random = new Random();
        return usenameList.get(random.nextInt(usenameList.size()));
    }

    public String getPass(){
        var passwordList = Arrays.stream(passwordCredentials.getText().split("\n")).filter(e -> e.contains("_")).toList();
        Random random = new Random();
        return passwordList.get(random.nextInt(passwordList.size()));
    }

    public CartPage logInToApp(){
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(getLogin());
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(getPass());
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        return new CartPage();
    }


}
