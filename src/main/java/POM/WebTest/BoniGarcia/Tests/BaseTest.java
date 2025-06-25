package POM.WebTest.BoniGarcia.Tests;

import Base.BaseActions.BaseActionsV1;
import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected BaseActionsV1 actions;
    private DriverFactoryV1 factory;
    MainPage mainPage;

    //    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("10") int timeout){
        factory = new DriverFactoryV1(browser, timeout);
        this.driver = factory.getDriver();
        this.wait = factory.getWait();
        this.actions = new BaseActionsV1(driver, wait);
        mainPage = new MainPage(driver, wait);
    }

    @AfterMethod
    void shutDown(){
        factory.quit();
    }
}
