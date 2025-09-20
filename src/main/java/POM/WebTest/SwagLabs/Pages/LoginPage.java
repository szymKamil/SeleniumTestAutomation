package POM.WebTest.SwagLabs.Pages;

import Base.BaseTest.DriverFactoryV1;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Random;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
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
        wait.until(ExpectedConditions.visibilityOf(loginCredentials));
        var usenameList = Arrays.stream(loginCredentials.getText().split("\n")).filter(e -> e.contains("_user")).filter(e -> !e.contains("locked")).toList();
        Random random = new Random();
        return usenameList.get(random.nextInt(usenameList.size()));
    }

    public String getPass(){
        wait.until(ExpectedConditions.visibilityOf(passwordCredentials));
        var passwordList = Arrays.stream(passwordCredentials.getText().split("\n")).filter(e -> e.contains("_")).toList();
        Random random = new Random();
        return passwordList.get(random.nextInt(passwordList.size()));
    }

    public InventoryPage logInToAppOnRandomCredentials(){
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(getLogin());
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(getPass());
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        return new InventoryPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
    }

    public InventoryPage logInToApp(String login, String password){
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(login);
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return new InventoryPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
    }

}
