package POM.WebTest.BoniGarcia.Tests;

import Base.BaseTest.BaseTestV1;
import POM.WebTest.BoniGarcia.Pages.GarciaMainPage;
import POM.WebTest.BoniGarcia.Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class MainTest {

    WebDriver driver;
    BaseTestV1 base;
    WebDriverWait wait;
    MainPage mainPage;


    @BeforeMethod
    public void config(){
         base = new BaseTestV1("Chrome", 3000);
         driver = base.getWebDriver();
         wait = base.getWait();
    }


    @AfterMethod
    void shutDown(){
        driver.quit();

    }



    @Test
    public void mainPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.         *
         */
        driver.get("http://www.google.pl");
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));



    }


}
