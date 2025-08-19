package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MouseOver extends AbstractPage{


    Actions actions;


    public MouseOver(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
        actions = new Actions(this.driver);
    }

    //Elementy na stronie
    @FindBy(css = "div > img.img-fluid")
    List<WebElement> imgList;

    private static final String IMG_CAPTION_XPATH  = "//*[text()='%s']";

    //Metody testowe
    public WebElement getElementByCaption(String caption) {
        By by = By.xpath(String.format(IMG_CAPTION_XPATH, caption));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(By.xpath(String.format(IMG_CAPTION_XPATH, caption)));
    }

    private static final String CAPTION_TEXT_XPATH = "(//div[@class='caption']/p)[%d]";

    public WebElement getCaptionElementByIndex(int index) {
        By by = By.xpath(String.format(CAPTION_TEXT_XPATH, index + 1));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }


    public void hoverOverImg(int index){
           actions.moveToElement(imgList.get(index)).perform();
    }




}
