package Selenium.RahulAcademy;

import Base.Drivers.DriverFactory;
import POM.WebTest.RahulAcademy.DataProviders.DataProviderLoginFormTest;
import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.Listener.TestNGListener;
import POM.WebTest.RahulAcademy.Pages.BaseTest.BaseTest;
import POM.WebTest.RahulAcademy.Pages.ShopTest.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopLoginPageForm;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;
import org.slf4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(TestNGListener.class)
public class ShopPageTests extends BaseTest {


    private static final Logger log = DriverFactory.getLogger();
    ShopLoginPageForm pageShopLoginForm;
    ShopPage shopPage;
    CheckoutPage checkoutPage;

    @Test(dataProvider = "logInShopData", dataProviderClass = DataProviderLoginFormTest.class)
    public void loginFormVerification(String radio, String role){
        ShopLoginPageForm pageShopLoginForm = new ShopLoginPageForm();
        pageShopLoginForm.insertLogin().insertPassword().choseRadio(radio).selectForms(role)
                .selectTermsAndConditions()
                .clickSignIn();
    }

    @Test()
    public void verifyShopIsOnline(){
        pageShopLoginForm = new ShopLoginPageForm();
        shopPage = new ShopPage();
        shopPage.logInToAppAs("Admin", "Student").verifyShopIsOnline().verifySlider();
    }

    @Test
    public void verifySlider(){
        pageShopLoginForm = new ShopLoginPageForm();
        shopPage = new ShopPage();
        pageShopLoginForm = new ShopLoginPageForm();
        shopPage.logInToAppAs("Admin", "Student").verifySlider();
    }

    @Test
    public void shoppingCartTest(){
        checkoutPage = new CheckoutPage();
        shopPage = new ShopPage();
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result);
    }

    @Test
    public void shoppingCartRemoveFromCartTest(){
        checkoutPage = new CheckoutPage();
        shopPage = new ShopPage();
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result).removeProductsFromCart();
    }


    @Test
    public void shoppingCartFinaliseOrderTest(){
        shopPage = new ShopPage();
        CartPickResult result = shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage = new CheckoutPage();
        checkoutPage.verifyNumberOfProducts(result).goToCheckout().finaliseOrder("Lublin");
    }



}
