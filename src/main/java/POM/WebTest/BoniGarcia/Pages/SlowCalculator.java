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



    public SlowCalculator(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "delay")
    public WebElement delayInput;

    @FindBy(id = "calculator")
    public WebElement calculator;

    @FindBy(css = "div.screen")
    public WebElement screen;

    @FindBy(id = "spinner")
    public WebElement spinner;


    //Metody testowe
    public void setCalcDelay(int calcDelay){
        wait.until(ExpectedConditions.visibilityOf(delayInput)).sendKeys(Keys.BACK_SPACE, Integer.toString(calcDelay));
    }

    public void useCalculator(String inputs){
        wait.until(ExpectedConditions.visibilityOf(calculator));
            for (char c : inputs.toCharArray()) {
                useCalcBtn(String.valueOf(c));
            }
    }

    public void useCalcBtn(String x) {
        calculator.findElement(By.xpath(String.format("//*[text()='%s']", x)))
                .click();
    }

    public String getResultOfCalc(){
        wait.until(ExpectedConditions.invisibilityOf(spinner));
        return screen.getText();

    }
}
