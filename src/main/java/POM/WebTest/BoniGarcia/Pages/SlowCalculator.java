package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SlowCalculator extends AbstractPage{



    public SlowCalculator(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "delay")
    WebElement delayInput;

    @FindBy(id = "calculator")
    WebElement calculatorElement;

    @FindBy(css = "div.screen")
    WebElement calculatorScreen;

    @FindBy(id = "spinner")
    WebElement spinnerIcon;


    //Metody testowe
    public void setCalcDelay(int calcDelay){
        wait.until(ExpectedConditions.visibilityOf(delayInput)).clear();
        delayInput.sendKeys(Keys.BACK_SPACE, Integer.toString(calcDelay));
        String delayModified = delayInput.getAttribute("value");
        log.info("Kalkulator ma delay ustawiony na {}", delayModified);
    }

    public void useCalculator(String inputs){
        wait.until(ExpectedConditions.visibilityOf(calculatorElement));
            for (char c : inputs.toCharArray()) {
                useCalcBtn(String.valueOf(c));
            }
    }

    public void useCalcBtn(String x) {
        calculatorElement.findElement(By.xpath(String.format("//*[text()='%s']", x)))
                .click();
    }

    public String getResultOfCalc(){
        wait.until(ExpectedConditions.invisibilityOf(spinnerIcon));
        String result = wait.until(ExpectedConditions.visibilityOf(calculatorScreen)).getText();
        log.info("Wynik działania na kalkulatorze to: {}", result);
        return result;

    }
}
