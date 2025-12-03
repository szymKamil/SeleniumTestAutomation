package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MultilanguagePage extends AbstractPage {



    public MultilanguagePage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "content")
    WebElement contentParagraphs;


    //Metody testowe
    public String getParagraphsInfo(){
        return wait.until(ExpectedConditions.visibilityOf(contentParagraphs)).getText().replace("\n", ", ");
    }

}
