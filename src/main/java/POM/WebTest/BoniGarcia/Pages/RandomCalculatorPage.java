package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RandomCalculatorPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;


    public RandomCalculatorPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "percent")
    WebElement percentInput;

    @FindBy(id = "correct")
    WebElement correctInput;

    @FindBy(xpath = "//span[contains(@class, 'btn')]")
    List<WebElement> abstractBtns;

    @FindBy(css = "div.screen")
    WebElement calcScreen;



    public void setPercentOfCorrectResults(String setPercent){
        wait.until(ExpectedConditions.elementToBeClickable(percentInput)).clear();
        percentInput.sendKeys(setPercent);
    }
    public void setCorrectTimesToRun(String setCorrect){
        wait.until(ExpectedConditions.elementToBeClickable(correctInput)).clear();
        correctInput.sendKeys(setCorrect);
    }

    public String calculate(String calculation){

        char[] charArray = calculation.toCharArray();
        abstractBtns.stream().filter(e -> e.getText().contains("C")).findAny().ifPresent(WebElement::click);
        for (char cha : charArray){
            Optional<WebElement> btn = Optional.of(abstractBtns.stream()
                    .filter(e -> e.getText()
                            .contains(Character.toString(cha)))
                    .findFirst()
                    .get());
            btn.ifPresent(WebElement::click);

        }
        return calcScreen.getText();
    }



}
