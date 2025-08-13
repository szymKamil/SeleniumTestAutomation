package POM.WebTest.BoniGarcia.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org. slf4j. Logger;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class BaseTest  {
    /*------Bazowe-----------*/
    protected WebDriver driver;
    protected WebDriverWait wait;
    private DriverFactoryV1 factory;

    Logger log;
    Random random;
    /*-----------------*/



    /*------Klasy stron-----------*/
    MainPage mainPage;
    WebForm webForm;
    Navigation navigationPage;
    AbstractPage ap;
    DropdownMenu dropdownMenu;
    MouseOver mouseOver;
    DragAndDrop dragAndDrop;
    CanvasPage canvasPage;
    LoadingImagesPage loadingImagesPage;
    SlowCalculator slowCalculator;
    LongPage longPage;
    InfiniteScrollPage infiniteScroll;
    ShadowDomPage shadowDomPage;
    CookiesPage cookiesPage;
    FramesPage framesPage;
    IFramePage iFramePage;
    DialogBoxesPage dialogBoxesPage;
    WebStoragePage webStoragePage;
    GeolocationPage geolocationPage;
    NotificationPage notificationPage;
    UserMediaPage userMediaPage;
    MultilanguagePage multilanguagePage;
    ConsoleLogsPage consoleLogsPage;
    LoginFormPage loginFormPage;
    RandomCalculatorPage randomCalculatorPage;
    FileDownloadPage fileDownloadPage;
    ABTestingPage abTestingPage;
    DataTypesPage dataTypesPage;
    /*-----------------*/

    Supplier<String> randomGeneratedText = () -> {
        random = new Random(2);
        ArrayList<Character> randomTextInput = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            randomTextInput.add(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            randomTextInput.add(ch);
        }
        randomTextInput.add('.');
        randomTextInput.add(',');
        randomTextInput.add(' ');
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 60; i++){
            stringBuilder.append(randomTextInput.get(random.nextInt(randomTextInput.size())));
        }
        return stringBuilder.toString();
    };


    //    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("25") int timeout) throws Exception {
        factory = new DriverFactoryV1(browser, timeout);
        this.driver = factory.getDriver();
        this.wait = factory.getWait();
        this.log = factory.getLogger();
        mainPage = new MainPage(driver, wait, log);
        webForm = new WebForm(driver, wait, log);
        navigationPage = new Navigation(driver, wait, log);
        dropdownMenu = new DropdownMenu(driver, wait, log);
        mouseOver = new MouseOver(driver, wait, log);
        dragAndDrop = new DragAndDrop(driver, wait, log);
        canvasPage = new CanvasPage(driver, wait, log);
        loadingImagesPage = new LoadingImagesPage(driver, wait, log);
        slowCalculator = new SlowCalculator(driver, wait, log);
        longPage = new LongPage(driver, wait, log);
        infiniteScroll = new InfiniteScrollPage(driver, wait);
        shadowDomPage = new ShadowDomPage(driver, wait, log);
        cookiesPage = new CookiesPage(driver, wait, log);
        framesPage = new FramesPage(driver, wait, log);
        iFramePage = new IFramePage(driver, wait, log);
        dialogBoxesPage = new DialogBoxesPage(driver, wait, log);
        webStoragePage = new WebStoragePage(driver, wait, log);
        geolocationPage = new GeolocationPage(driver, wait, log);
        notificationPage = new NotificationPage(driver, wait, log);
        userMediaPage = new UserMediaPage(driver, wait, log);
        multilanguagePage = new MultilanguagePage(driver, wait, log);
        consoleLogsPage = new ConsoleLogsPage(driver, wait, log);
        loginFormPage = new LoginFormPage(driver, wait, log);
        randomCalculatorPage = new RandomCalculatorPage(driver, wait, log);
        fileDownloadPage = new FileDownloadPage(driver, wait, log);
        abTestingPage = new ABTestingPage(driver, wait, log);
        dataTypesPage = new DataTypesPage(driver, wait, log);
    }


    @AfterMethod
    void shutDown(){
        factory.quit();
    }
}
