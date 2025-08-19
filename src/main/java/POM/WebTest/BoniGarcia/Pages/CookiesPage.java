package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.CookieEntry;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
    public WebElement refreshCookiesBtn;

    @FindBy(id = "cookies-list")
    public WebElement cookiesParagraph;

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

}
