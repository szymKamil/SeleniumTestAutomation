package POM.WebTest.RahulAcademy.Helpers;

import POM.WebTest.RahulAcademy.Pages.ShopTest.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;

import java.util.Map;

public class CartPickResult {

    private final ShopPage shopPage;
    private final Map<String, Integer> numOfProductPicked;

    public CartPickResult(ShopPage shopPage, Map<String, Integer> numOfProductPicked) {
        this.shopPage = shopPage;
        this.numOfProductPicked = numOfProductPicked;
    }
    public CheckoutPage goToCheckout() {
        return shopPage.goToCheckout();
    }

    public Map<String, Integer> getNumOfProductPicked() {
        return numOfProductPicked;
    }


}
