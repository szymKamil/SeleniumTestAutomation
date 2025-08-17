package POM.WebTest.RahulAcademy.Pages.ShopTest;

import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;

public class CheckoutPage {

    Logger log;
    WebDriver driver;
    WebDriverWait wait;
    WebElementActions actions;
    ShopPage shopPage;

    public CheckoutPage( WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        actions = new WebElementActions(driver, wait);
        shopPage = new ShopPage(driver, wait, log);
    }



    public CheckoutPage verifyNumberOfProducts(CartPickResult cartPickResult){
        cartPickResult.getNumOfProductPicked().forEach((product, count) ->
        log.info("Produkt {} został dodany {} razy", product, count)
        );
        try {
            Thread.sleep(Duration.ofSeconds(15));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }










}
