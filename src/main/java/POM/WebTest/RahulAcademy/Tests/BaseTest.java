package POM.WebTest.RahulAcademy.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Listener.Listener;
import POM.WebTest.RahulAcademy.Pages.ShopTest.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopLoginPageForm;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;

public class BaseTest {


    WebDriver driver;
    WebDriverWait wait;
    private DriverFactoryV1 factory;
    private Logger log;
    WebElementActions actions;



    protected final URI uri = URI.create("http://192.168.1.108:4444");
    private final String formTestPage = "https://rahulshettyacademy.com/locatorspractice/";
    private final String shopLoginPage = "https://rahulshettyacademy.com/loginpagePractise/\n";

    //Klasy stron
    protected LoginFormPage loginPageTest;
    ShopLoginPageForm pageShopLoginForm;
    ShopPage shopPage;
    CheckoutPage checkoutPage;

    @Parameters({"browser", "timeout"})
    @BeforeClass()
    public void config(@Optional("Chrome") String browser, @Optional("25") int timeout) throws MalformedURLException {
        factory = new DriverFactoryV1(browser, timeout /*, uri.toURL()*/);
        this.driver = factory.getDriver();
        this.wait = factory.getWait();
        this.log = factory.getLogger();
        driver = new EventFiringDecorator<>(new Listener()).decorate(driver);
//        actions = new WebElementActions(driver, wait);
        loginPageTest = new LoginFormPage(driver, wait, log);
        pageShopLoginForm = new ShopLoginPageForm(driver, wait, log);
        shopPage = new ShopPage(driver, wait, log);
        checkoutPage = new CheckoutPage(driver, wait, log);
    }


    @BeforeMethod()
    public void config(Method method)  {
        log.info("Rozpoczynam test: {}", method.getName());
        Class<?> testClass = method.getDeclaringClass();
        if (testClass.equals(LocatorsFormLoginTests.class)) {
            driver.get(formTestPage);
        } else if (testClass.equals(ShopPageTests.class)) {
            driver.get(shopLoginPage);
        } else {
            log.error("Błędnie podany adres URL, test nie został uruchomiony.");
            throw new RuntimeException("Nie mogę uruchomić testu z powodu braku URL do strony testowej!!!");
        }

    }

    @AfterMethod
    public void testInfo(ITestResult test, Method method){
        if (ITestResult.FAILURE == test.getStatus()){
            log.error("Test się nie powiódł!: {}", method.getName());
        } else if (ITestResult.SUCCESS == test.getStatus()) {
            log.info("Test zakończony pomyślnie: {}", method.getName());
        }
    }

    @AfterClass
    void shutDown(){
       log.info("Testy zostały ukończone.");
       if (driver != null) {
           driver.quit();
       }
    }







}
