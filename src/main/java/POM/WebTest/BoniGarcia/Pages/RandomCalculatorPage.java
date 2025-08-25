package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.List;

public class RandomCalculatorPage extends AbstractPage{



    public RandomCalculatorPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "percent")
    WebElement PERCENT_INPUT;

    @FindBy(id = "correct")
    WebElement CORRECT_INPUT;

    @FindBy(xpath = "//span[contains(@class, 'btn')]")
    List<WebElement> ABSTRACT_CALC_BTNS;

    @FindBy(css = "div.screen")
    WebElement CALC_SCREEN_ELEMENT;


    //Metody testowe
    public void setPercentOfCorrectResults(String setPercent){
        wait.until(ExpectedConditions.elementToBeClickable(PERCENT_INPUT)).clear();
        PERCENT_INPUT.sendKeys(setPercent);
    }
    public void setCorrectTimesToRun(String setCorrect){
        wait.until(ExpectedConditions.elementToBeClickable(CORRECT_INPUT)).clear();
        CORRECT_INPUT.sendKeys(setCorrect);
    }

    public String calculate(String calculation){
        char[] charArray = calculation.toCharArray();
        ABSTRACT_CALC_BTNS.stream().filter(e -> e.getText().contains("C")).findAny().ifPresent(WebElement::click);
        for (char ch : charArray){
            WebElement btn =
                ABSTRACT_CALC_BTNS.stream()
                        .filter(e -> e.getText()
                                .contains(Character.toString(ch)))
                        .findFirst()
                        .get();
                btn.click();
        }
        return CALC_SCREEN_ELEMENT.getText();
    }
}
