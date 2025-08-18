package POM.WebTest.SwagLabs.Tests;

import Base.BaseTest.DriverFactoryV1;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;

public class BaseTest  {

    WebDriver driver;
    WebDriverWait wait;
    Logger logger;
    private DriverFactoryV1 factory;



    @BeforeClass
    public void initiateDriver(){
        factory = new DriverFactoryV1()


    }













}
