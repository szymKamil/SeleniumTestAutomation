package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/***
 * Klasa bazowa, zbierająca abstrakcyjne elementy, występujące na każdej ze stron testowych.
 */
public class AbstractPage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;


    public AbstractPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = DriverFactory.getWait();
    }

    //Elementy
    public static final String PAGE_TITLE = "Hands-On Selenium WebDriver with Java";
    public static final String COPYRIGHTS = "Copyright © 2021-2025";
    public static final By MAIN_HEADER = By.xpath("//h1[@class='display-4']");
    public static final By MAIN_ICON = By.cssSelector("img.img-fluid");

    public static final By LEAD_SELECTOR = By.cssSelector("p.lead");
    public static final By COPYRIGHTS_SELECTOR = By.cssSelector("span.text-muted");

    public void verifyAbstractPage(){
        verifyMainHeader();
        verifyImage();
        verifyCopyrights();
    }


    public void verifyCopyrights() {
        Optional.of(getCopySpan()).map(WebElement::getText)
                .ifPresent(text -> assertThat(text).contains(COPYRIGHTS));
    }

    public void verifyMainHeader() {
        WebElement header = getMainHeader();
        if (header != null) {
            assertThat(header.isDisplayed()).isTrue();
            assertThat(header.getText()).isEqualTo(AbstractPage.PAGE_TITLE);
        }
    }

    public void verifyImage() {
        var image = getImage();
        assertThat(image)
                .withFailMessage("Nie znaleziono obrazu na stronie!")
                .isNotNull();
        assertThat(image.isDisplayed())
                .withFailMessage("Obraz jest niewidoczny na stronie!")
                .isTrue();
    }

    // Gettery
    private WebElement getMainHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_HEADER));
    }

    private WebElement getImage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_ICON));
    }


    private WebElement getCopySpan() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(COPYRIGHTS_SELECTOR));
    }

}
