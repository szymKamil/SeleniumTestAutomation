package POM.WebTest.BoniGarcia.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.BoniGarcia.Pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org. slf4j. Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class BaseTest  {
    /*------Bazowe-----------*/
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;
    /*-----------------*/
    final static URI LOCAL_URL =  URI.create("http://localhost:4444/");
    final static URI DOCKER_URL = URI.create("http://192.168.1.104:5555/");


    /*------Klasy stron-----------*/
    AbstractPage ap;
    /*MainPage mainPage;
    WebForm webForm;
    Navigation navigationPage;
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
    DataTypesPage dataTypesPage;*/
    /*-----------------*/



    //    @Listeners(TestListener.class)
    @Parameters({"browser", "timeout"})
    @BeforeMethod
    public void config(@Optional("Chrome") String browser, @Optional("55") int timeout) throws Exception {
        DriverFactoryV1.initDriver("chrome", timeout/*, LOCAL_URL.toURL()*/);
        this.driver = DriverFactoryV1.getDriver();
        this.wait = DriverFactoryV1.getWait();
        this.log = DriverFactoryV1.getLogger();
        ap = new AbstractPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait(), DriverFactoryV1.getLogger());


      /*  mainPage = new MainPage(driver, wait, log);
        webForm = new WebForm(driver, wait, log);
        navigationPage = new Navigation(driver, wait, log);
        dropdownMenu = new DropdownMenu(driver, wait, log);
        mouseOver = new MouseOver(driver, wait, log);
        dragAndDrop = new DragAndDrop(driver, wait, log);
        canvasPage = new CanvasPage(driver, wait, log);
        loadingImagesPage = new LoadingImagesPage(driver, wait, log);
        slowCalculator = new SlowCalculator(driver, wait, log);
        longPage = new LongPage(driver, wait, log);
        infiniteScroll = new InfiniteScrollPage(driver, wait, log);
        shadowDomPage = new ShadowDomPage(driver, wait, log);
        cookiesPage = new CookiesPage(driver, wait, log);
        framesPage = new FramesPage(driver, wait, log);
        iFramePage = new IFramePage(driver, wait, log);
        dialogBoxesPage = new DialogBoxesPage(driver, wait, log);*/
//        webStoragePage = new WebStoragePage(driver, wait, log);
//        geolocationPage = new GeolocationPage(driver, wait, log);
//        notificationPage = new NotificationPage(driver, wait, log);
//        userMediaPage = new UserMediaPage(driver, wait, log);
/*        multilanguagePage = new MultilanguagePage(driver, wait, log);
        consoleLogsPage = new ConsoleLogsPage(driver, wait, log);
        loginFormPage = new LoginFormPage(driver, wait, log);
        randomCalculatorPage = new RandomCalculatorPage(driver, wait, log);
        fileDownloadPage = new FileDownloadPage(driver, wait, log);
        abTestingPage = new ABTestingPage(driver, wait, log);
        dataTypesPage = new DataTypesPage(driver, wait, log);*/
    }


    @AfterMethod
    void tearDown(){
        DriverFactoryV1.quit();
    }
}
