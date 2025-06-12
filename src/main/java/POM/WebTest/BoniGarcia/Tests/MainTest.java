package POM.WebTest.BoniGarcia.Tests;

import Base.BaseTest.BaseTestV1;
import Base.BaseTest.DriverManager;
import POM.WebTest.BoniGarcia.Pages.GarciaMainPage;
import POM.WebTest.BoniGarcia.Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainTest {

    MainPage mainPage;

    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("10") int timeout){
        DriverManager.initDriver(browser, timeout);
        mainPage = new MainPage(DriverManager.getDriver());
    }


    @AfterMethod
    void shutDown(){
        DriverManager.quitDriver();
    }



    @Test
    public void mainPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.         *
         */
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = DriverManager.getWait();

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));



    }

    @Test
    public void mainPageTest1(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.         *
         */
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = DriverManager.getWait();

        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));


    }


}
