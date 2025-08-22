package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class ShadowDomPage extends AbstractPage{


    public ShadowDomPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "content")
    WebElement SHADOW_ROOT_DIV;

    //Metody testowe
    public String getShadowRootContent(){
        SearchContext searchContext = SHADOW_ROOT_DIV.getShadowRoot();
        String shadowDOMtext = searchContext.findElement(By.cssSelector("p")).getText();
        log.info("ShadowDOM posiada tekst: {}", shadowDOMtext);
        return shadowDOMtext;
    }


}
