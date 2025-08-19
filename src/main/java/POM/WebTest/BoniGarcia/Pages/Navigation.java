package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class Navigation extends AbstractPage{



    public Navigation(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(xpath = "//h1[@class='display-6']")
    public WebElement mainHeader;

    @FindBy(xpath = "//a[contains(text(), 'Next')]")
    public WebElement nextBtn;

    @FindBy(xpath = "//a[contains(text(), 'Previous')]")
    public WebElement prevBtn;

    @FindBy(className = "lead")
    public WebElement leadParagraph;

    @FindBy(xpath = "(//li[contains(@class, 'active')])[1]")
    public WebElement btnActive;

    //Metody testowe
    public void btnNext(){
        wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
    }

    public void btnPrev(){
        wait.until(ExpectedConditions.elementToBeClickable(prevBtn)).click();
    }

    public void activeBtnInfo(){
        String btnNumber = wait.until(ExpectedConditions.visibilityOf(btnActive)).getText();
        log.info("Aktywnym przyciskiem jest aktualnie {}", btnNumber);
    }

}
