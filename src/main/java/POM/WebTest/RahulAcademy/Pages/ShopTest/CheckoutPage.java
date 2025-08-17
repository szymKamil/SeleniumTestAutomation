package POM.WebTest.RahulAcademy.Pages.ShopTest;

import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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

    //Elementy
    By cartProduct = By.cssSelector("tr td div div h4 a");
    By quantityProducts = By.id("exampleInputEmail1");



    public CheckoutPage verifyNumberOfProducts(CartPickResult cartPickResult) {
        cartPickResult.getNumOfProductPicked()
                .forEach((product, count) ->
                        log.info("Produkt {} został dodany {} razy", product, count)
                );

        List<WebElementActions> products = actions.findAll(cartProduct);
        List<WebElementActions> productsQuantity = actions.findAll(quantityProducts);

        Stream.iterate(0, i -> i + 1)
                .limit(products.size())
                .forEach(i -> {
                    String productName = products.get(i).getText();
                    String expectedQuantity = productsQuantity.get(i).getAttribute("value");

                    // Pobieramy faktyczną ilość z mapy jako String
                    Integer actualQuantityInt = cartPickResult.getNumOfProductPicked().get(productName);
                    String actualQuantity = actualQuantityInt != null ? actualQuantityInt.toString() : "0";
                    log.info("Produkt {} znalazł się w koszyku w ilości {}", productName, actualQuantity);
                    assertThat(actualQuantity).isEqualTo(expectedQuantity);
                });



        return this;
        }
}












