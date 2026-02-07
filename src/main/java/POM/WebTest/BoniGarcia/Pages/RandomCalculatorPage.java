package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RandomCalculatorPage extends AbstractPage{



    public RandomCalculatorPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "percent")
    WebElement percentInput;

    @FindBy(id = "correct")
    WebElement correctInput;

    @FindBy(xpath = "//span[contains(@class, 'btn')]")
    List<WebElement> abstractCalcBtns;

    @FindBy(css = "div.screen")
    WebElement calcScreenElement;


    //Metody testowe
    public void setPercentOfIncorrectResults(String setPercent){
        wait.until(ExpectedConditions.elementToBeClickable(percentInput)).clear();
        percentInput.sendKeys(setPercent);
    }
    public void setCorrectTimesToRun(String setCorrect){
        wait.until(ExpectedConditions.elementToBeClickable(correctInput)).clear();
        correctInput.sendKeys(setCorrect);
    }

    public String calculate(String calculation){
        char[] charArray = calculation.toCharArray();
        abstractCalcBtns.stream().filter(e -> e.getText().contains("C")).findAny().ifPresent(WebElement::click);
        for (char ch : charArray){
            WebElement btn =
                abstractCalcBtns.stream()
                        .filter(e -> e.getText()
                                .contains(Character.toString(ch)))
                        .findFirst()
                        .get();
                btn.click();
        }
        return calcScreenElement.getText();
    }
}
