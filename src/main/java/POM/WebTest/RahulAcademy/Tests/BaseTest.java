package POM.WebTest.RahulAcademy.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.LoginPageTest;
import POM.WebTest.RahulAcademy.Utils.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.*;

public class BaseTest {


    protected WebDriver driver;
    protected WebDriverWait wait;
    private DriverFactoryV1 factory;
    private Logger log;
    WebElementActions actions;

    //Klasy stron
    LoginPageTest loginPageTest;


    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("25") int timeout) {
        factory = new DriverFactoryV1(browser, timeout);
        this.driver = factory.getDriver();
        this.wait = factory.getWait();
        this.log = factory.getLogger();
        loginPageTest = new LoginPageTest(driver, wait, log);

    }

    @AfterMethod
    void shutDown(){
       log.info("Test ukończony.");
       factory.quit();
    }





}
