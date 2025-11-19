package POM.WebTest.RahulAcademy.Pages.ShopTest;

import Base.Drivers.DriverFactory;
import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutPage extends ShopPage {

    Logger log = LoggerFactory.getLogger("Logger");
    WebElementActions actions;
    ShopPage shopPage;

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        actions = new WebElementActions(DriverFactory.getDriver(), DriverFactory.getWait());
        shopPage = new ShopPage(DriverFactory.getDriver(), DriverFactory.getWait());
    }

    //Elementy
    By cartProduct = By.cssSelector("tr td div div h4 a");
    By quantityProducts = By.id("exampleInputEmail1");
    By totalCartAmount= By.xpath("//td[@class='text-right']/h3/strong");
    By removeItemFromCartBtn = By.cssSelector("button.btn.btn-danger");
    By goToCheckoutBtn = By.cssSelector("button.btn.btn-success");
    By checkoutAdresInput = By.id("country");
    By checkoutTermsAndConditions = By.cssSelector("input#checkbox2 + label");
    By purchaseBtn = By.cssSelector("form input[type=submit]");
    By successInfo = By.cssSelector("div.alert");
    By successInfoCloseBtn = By.cssSelector("div.alert a.close");


    //Metody
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
                    String actualQuantityInt = String.valueOf(cartPickResult.getNumOfProductPicked().get(productName));
                    log.info("Produkt {} znalazł się w koszyku w ilości {}", productName, actualQuantityInt);
                    assertThat(actualQuantityInt).isEqualTo(expectedQuantity);
                });
        return this;
        }

    public CheckoutPage removeProductsFromCart(){
        String initialAmount = actions.find(totalCartAmount).getText().split(" ")[1];
        List<WebElementActions> removeBtns = actions.findAll(removeItemFromCartBtn);
        for (WebElementActions removeBtn : removeBtns){
            removeBtn.click();
        }
        String modifiedAMount = actions.find(totalCartAmount).getText().split(" ")[1];
        log.info("Początkowo kwota wynosiła {}, po usunięciu produktów wynosi {}", initialAmount, modifiedAMount);
        assertThat(initialAmount).isNotEqualTo(modifiedAMount);
        return this;
    }

    public CheckoutPage goToCheckout(){
        actions.find(goToCheckoutBtn).click();
        return this;
    }

    public CheckoutPage finaliseOrder(String adress){
        actions.find(checkoutAdresInput).enterText(adress);
        actions.find(checkoutTermsAndConditions).click();
        actions.find(purchaseBtn).click();
        String successInfoText = actions.find(successInfo).getText();
        log.info("Komunikat brzmi {}", successInfoText);
        actions.find(successInfoCloseBtn).click();
        return this;
    }

}












