package POM.WebTest.BoniGarcia.Tests;

import Base.BaseActions.BaseActionsV1;
import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.*;


public class MainTest extends BaseTest {


    @Test
    public void mainPageTest1(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.
         */
        actions = new BaseActionsV1(driver, wait);
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));
        mainPage.getBtn("Web form");
        assertThat(driver.findElements(mainPage.img)).isNotEmpty().hasSize(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));
        actions.click(mainPage.getBtn("Web form"));



    }


}
