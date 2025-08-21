package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationPage extends AbstractPage{


    private StringBuilder LOREM_IPSUM;

    public NavigationPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(xpath = "//a[contains(text(), 'Next')]")
    public WebElement NEXT_BTN;

    @FindBy(xpath = "//a[contains(text(), 'Previous')]")
    public WebElement PREV_BTN;

    @FindBy(className = "lead")
    public WebElement LEAD_PARAGRAPH;

    @FindBy(xpath = "(//li[contains(@class, 'active')])[1]")
    public WebElement BTN_ACTIVE_INFO;

    //Metody testowe
    public void btnNext(){
        wait.until(ExpectedConditions.elementToBeClickable(NEXT_BTN)).click();
    }

    public void btnPrev(){
        wait.until(ExpectedConditions.elementToBeClickable(PREV_BTN)).click();
    }

    public void activeBtnInfo(){
        String btnNumber = wait.until(ExpectedConditions.visibilityOf(BTN_ACTIVE_INFO)).getText();
        log.info("Aktywnym przyciskiem jest aktualnie {}", btnNumber);
    }

    public void verifyBtns(){
        LOREM_IPSUM = new StringBuilder(getTextFromLead());
        activeBtnInfo();
        log.info("Pierwszy paragraf brzmi: {}", LOREM_IPSUM);
        btnNext();
        activeBtnInfo();
        LOREM_IPSUM = new StringBuilder(getTextFromLead());
        log.info("Drugi paragraf brzmi: {}", LOREM_IPSUM);
        btnNext();
        activeBtnInfo();
        LOREM_IPSUM = new StringBuilder(getTextFromLead());
        log.info("Trzeci paragraf brzmi: {}", LOREM_IPSUM);
        btnPrev();
        activeBtnInfo();
        btnPrev();
        btnPrev();
        activeBtnInfo();

    }

    //Getters

    public String getTextFromLead(){
        return wait.until(ExpectedConditions.visibilityOf(LEAD_PARAGRAPH)).getText();
    }




}
