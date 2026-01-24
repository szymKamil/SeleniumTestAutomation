package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import POM.WebTest.BoniGarcia.Utils.CookieEntry;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookiesPage extends AbstractPage{

    Logger log = LoggerFactory.getLogger(CookiesPage.class);
    Actions actions;

    public CookiesPage() {
        super();
        PageFactory.initElements(DriverFactory.getDriver(), this);
        actions = new Actions(driver);
    }

    //Główne elementy strony
    @FindBy(id = "refresh-cookies")
    WebElement refreshCookiesBtn;

    @FindBy(id = "cookies-list")
    WebElement cookiesParagraph;

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
            var element = wait.until(ExpectedConditions.elementToBeClickable(refreshCookiesBtn));
            if (element != null){
                element.click();
            } else {
                log.error("Nie mona odnaleźć przycisku {}", element);
        }
    }

    public String getCookiesText(){
        wait.until(ExpectedConditions.visibilityOf(cookiesParagraph));
        String cookiesParagraphText = cookiesParagraph.getText();
        log.info("Ciasteczka są następujące: {}", cookiesParagraphText);
        return cookiesParagraphText;
    }

}
