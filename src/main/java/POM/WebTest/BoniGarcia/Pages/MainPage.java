package POM.WebTest.BoniGarcia.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;


public class MainPage extends AbstractPage {


    public MainPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
    }

    public static final String boniGarciaMainURL = "https://bonigarcia.dev/selenium-webdriver-java/";
    public static final By containers = By.cssSelector("div.col-md-4, py-2");
    public static final By lead = By.cssSelector("p.lead");
    public static final By copySpan = By.cssSelector("span.text-muted");
    public static WebElement btn;


    public WebElement getBtn(String btnName) {
        By btn = By.xpath("//a[@class='btn btn-outline-primary mb-2' and text()='" + btnName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(btn));
    }


    public void goToSubPage(String btnName) {
        btn = getBtn(btnName);
        wait.until(ExpectedConditions.visibilityOf(btn)).click();
    }



}