package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class ABTestingPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;


    public ABTestingPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
    }

    //Elementy strony
    @FindBy(id = "content")
    WebElement variationTypeDiv;

    @FindBy(css = "div h6")
    WebElement variationType;

    //Metody testowe
    public void verifyTypeOfVariation(){
        wait.until(ExpectedConditions.visibilityOf(variationType));
        String type = variationType.getText();
        if (type.endsWith("A")){
            log.info("Strona uruchomiła się w wariancie A");
        } else if (type.endsWith("B")) {
            log.info("Strona uruchomiła się w wariancie B");
        } else {
            log.error("Wystąpił błąd, nieznany typ");
        }
    }







}
