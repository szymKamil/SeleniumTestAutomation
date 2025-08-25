package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class SlowCalculator extends AbstractPage{



    public SlowCalculator(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "delay")
    public WebElement DELAY_INPUT;

    @FindBy(id = "calculator")
    public WebElement CALCULATOR_ELEMENT;

    @FindBy(css = "div.screen")
    public WebElement CALCULATOR_SCREEN;

    @FindBy(id = "spinner")
    public WebElement SPINNER_ICON;


    //Metody testowe
    public void setCalcDelay(int calcDelay){
        wait.until(ExpectedConditions.visibilityOf(DELAY_INPUT)).clear();
        DELAY_INPUT.sendKeys(Keys.BACK_SPACE, Integer.toString(calcDelay));
        String delayModified = DELAY_INPUT.getAttribute("value");
        log.info("Kalkulator ma delay ustawiony na {}", delayModified);
    }

    public void useCalculator(String inputs){
        wait.until(ExpectedConditions.visibilityOf(CALCULATOR_ELEMENT));
            for (char c : inputs.toCharArray()) {
                useCalcBtn(String.valueOf(c));
            }
    }

    public void useCalcBtn(String x) {
        CALCULATOR_ELEMENT.findElement(By.xpath(String.format("//*[text()='%s']", x)))
                .click();
    }

    public String getResultOfCalc(){
        wait.until(ExpectedConditions.invisibilityOf(SPINNER_ICON));
        String result = wait.until(ExpectedConditions.visibilityOf(CALCULATOR_SCREEN)).getText();
        log.info("Wynik działania na kalkulatorze to: {}", result);
        return result;

    }
}
