package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class LoginFormPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private JavascriptExecutor js;

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



}
