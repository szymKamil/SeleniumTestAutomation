package POM.WebTest.BoniGarcia.Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class PageLoadedVerification {


    public static ExpectedCondition<Boolean> pageIsLoaded() {
        return driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete");
    }

}
