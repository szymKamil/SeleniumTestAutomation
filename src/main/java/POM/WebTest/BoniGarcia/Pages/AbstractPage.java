package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;


/***
 * Klasa zawierająca abstrakcyjne elementy, występujące na każdej ze stron testowych Boni Garciego.
 */

public abstract class AbstractPage {

    WebDriver driver;
    WebDriverWait wait;
    Logger log ;


    public AbstractPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
    }

    //Elementy
    public static final String pageTitle = "Hands-On Selenium WebDriver with Java";
    public static final String leadText = "This site contains a collection of sample web pages to be tested with Selenium WebDriver. " +
            "Check out the O'Reilly book and the source code on GitHub.";
    public static final String copyrights = "Copyright © 2021-2025";
    public static final By mainHeader = By.xpath("//h1[@class='display-4']");
    public static final By img = By.cssSelector("img.img-fluid");

}
