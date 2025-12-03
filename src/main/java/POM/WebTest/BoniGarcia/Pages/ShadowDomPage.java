package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShadowDomPage extends AbstractPage{

    Logger log = LoggerFactory.getLogger(ShadowDomPage.class);

    public ShadowDomPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "content")
    WebElement shadowRootDiv;

    //Metody testowe
    public String getShadowRootContent(){
        SearchContext searchContext = shadowRootDiv.getShadowRoot();
        String shadowDOMtext = searchContext.findElement(By.cssSelector("p")).getText();
        log.info("ShadowDOM posiada tekst: {}", shadowDOMtext);
        return shadowDOMtext;
    }


}
