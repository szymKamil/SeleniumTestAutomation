package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.function.Function;

public class ABTestingPage extends AbstractPage {

    Logger log = DriverFactory.getLogger();


    public ABTestingPage() {
        super();
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    //Elementy strony
    @FindBy(id = "content")
    WebElement variationTypeDiv;

    @FindBy(css = "div h6")
    WebElement variationType;

    //Metody testowe
    public void verifyTypeOfVariation(){
        wait.until(ExpectedConditions.visibilityOf(variationTypeDiv));
        FluentWait<WebDriver> fluentWait = new FluentWait<>(DriverFactory.getDriver())
                .pollingEvery(Duration.ofSeconds(1)).ignoring(NullPointerException.class).withTimeout(Duration.ofSeconds(10));
            WebElement element = fluentWait.until(driver -> {
			WebElement el = driver.findElement(By.cssSelector("div h6"));
			if (el.isDisplayed()){
				return el;
			}
			else return null;
		});
        String type = "";
        if (element.isDisplayed()) {
            type = variationType.getText();
        } else {
            log.error("Nie znaleziono informacji o wariancie testu.");
        }
        if (type.endsWith("A")){
            log.info("Strona uruchomiła się w wariancie A");
        } else if (type.endsWith("B")) {
            log.info("Strona uruchomiła się w wariancie B");
        } else {
            log.error("Wystąpił błąd, nieznany typ");
        }
    }
}
