package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NavigationPage extends AbstractPage{

    Logger log = LoggerFactory.getLogger(NavigationPage.class);

    private StringBuilder LOREM_IPSUM;

    public NavigationPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
    }

    //Elementy na stronie
    @FindBy(xpath = "//a[contains(text(), 'Next')]")
    WebElement nextButton;

    @FindBy(xpath = "//a[contains(text(), 'Previous')]")
    WebElement prevButton;

    @FindBy(className = "lead")
    WebElement leadParagraph;

    @FindBy(xpath = "(//li[contains(@class, 'active')])[1]")
    WebElement btnActiveInfo;

    //Metody testowe
    public void btnNext(){
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void btnPrev(){
        wait.until(ExpectedConditions.elementToBeClickable(prevButton)).click();
    }

    public void activeBtnInfo(){
        String btnNumber = wait.until(ExpectedConditions.visibilityOf(btnActiveInfo)).getText();
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
        activeBtnInfo();

    }

    //Getters

    public String getTextFromLead(){
        return wait.until(ExpectedConditions.visibilityOf(leadParagraph)).getText();
    }




}
