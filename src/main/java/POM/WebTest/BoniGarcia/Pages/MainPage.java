package POM.WebTest.BoniGarcia.Pages;

import com.sun.jna.WString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MainPage {
    WebDriver driver;
    WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public String boniGarciaMainURL = "https://bonigarcia.dev/selenium-webdriver-java/";
    public By mainHeader = By.xpath("//h1[@class='display-4']");
    public By img = By.cssSelector("img.img-fluid");
    public By containers = By.cssSelector("div.col-md-4, py-2");

    public WebElement getBtn(String btnName) {
        By btn = By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()='" + btnName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btn));
    }


}
