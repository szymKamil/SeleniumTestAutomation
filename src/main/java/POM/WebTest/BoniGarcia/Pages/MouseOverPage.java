package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MouseOverPage extends AbstractPage {


    Actions actions;


    public MouseOverPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(css = "div > img.img-fluid")
    List<WebElement> imgList;

    private static final String IMG_CAPTION_XPATH = "//*[text()='%s']";
    private static final String CAPTION_TEXT_XPATH = "(//div[@class='caption']/p)[%d]";
    String[] imgCaptions = {"Compass", "Calendar", "Award", "Landscape"};


    //Metody testowe
    public WebElement choseAndWaitFormElementToBeVisible(String caption) {
        By by = By.xpath(String.format(IMG_CAPTION_XPATH, caption));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(By.xpath(String.format(IMG_CAPTION_XPATH, caption)));
    }

    public WebElement getCaptionElementByIndex(int index) {
        By by = By.xpath(String.format(CAPTION_TEXT_XPATH, index + 1));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }

    public void hoverOverImg(int index) {
        actions.moveToElement(imgList.get(index))
                .perform();
    }

    public void verifyImgCaptions() {
        for (int i = 0; i < imgCaptions.length; i++) {
            hoverOverImg(i);
            String imgCaption = getCaptionElementByIndex(i)
                    .getText();
            assertThat(imgCaption).isEqualTo(imgCaptions[i]);
            log.info("Element numer {} ma podpis: {}", (i + 1), imgCaption);

        }

    }
}
