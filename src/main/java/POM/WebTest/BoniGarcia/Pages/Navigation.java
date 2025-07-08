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

public class Navigation {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    private MainPage mainPage;


    public Navigation(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        mainPage = new MainPage(driver, wait, log);
        PageFactory.initElements(driver, this);
    }

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


    public void verifyNavigationPage() {
        wait.until(ExpectedConditions.visibilityOf(mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));
        assertThat(driver.findElement(mainPage.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(mainPage.copyrights);
    }

    public void navigationExampleTest(){
        wait.until(ExpectedConditions.visibilityOf(mainHeader));
        assertThat(mainHeader.getText()).isEqualTo("Navigation example");
        for (int i = 1; i < 4; i++){
            log.info("Lead na stronie {} brzmi: {}", i, leadParagraph.getText());
            log.info("Na stronie {} przycisk ma numer {}", i, btnActive.getText());
            if (i != 3) {
                nextBtn.click();
            }
        }
    }


}
