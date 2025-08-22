package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.CookieEntry;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class CookiesPage extends AbstractPage{

    Actions actions;

    public CookiesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
        actions = new Actions(this.driver);
    }

    //Główne elementy strony
    @FindBy(id = "refresh-cookies")
    WebElement REFRESH_COOKIES_BTN;

    @FindBy(id = "cookies-list")
    WebElement COOKIES_PARAGRAPH;

    //Metody testowe
    public void deleteAllCookies(){
        driver.manage().deleteAllCookies();
    }

    public void deleteCookie(String cookieName){
        driver.manage().deleteCookieNamed(cookieName);
    }

    public void addCookie(String cookieName, String cookieValue){
        driver.manage().addCookie(new Cookie(cookieName, cookieValue));
    }

    public void addCookies(CookieEntry... cookiesList){
        for(CookieEntry cookie : cookiesList){
            if (cookie != null) {
                addCookie(cookie.name(), cookie.value());
            }
        }
    }

    public void refreshCookiesBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(REFRESH_COOKIES_BTN)).click();
    }

    public String getCookiesText(){
        wait.until(ExpectedConditions.visibilityOf(COOKIES_PARAGRAPH));
        String cookiesParagraphText = COOKIES_PARAGRAPH.getText();
        log.info("Ciasteczka są następujące: {}", cookiesParagraphText);
        return cookiesParagraphText;
    }

}
