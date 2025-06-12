package POM.WebTest.BoniGarcia.Tests;

import Base.BaseTest.BaseTestV1;
import POM.WebTest.BoniGarcia.Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.assertj.core.api.Assertions.*;




public class MainTest {

    MainPage mainPage;
    BaseTestV1 base;

    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("10") int timeout){
        base = new BaseTestV1(browser, timeout);
        mainPage = new MainPage(base.getDriver(), base.getWait());
    }


    @AfterMethod
    void shutDown(){
        base.quit();
    }



    @Test
    public void mainPageTest1(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.
         */
        WebDriver driver = base.getDriver();
        WebDriverWait wait = base.getWait();

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));
        mainPage.getBtn("Web form");
        assertThat(driver.findElements(mainPage.img)).isNotEmpty().hasSize(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));


    }


}
