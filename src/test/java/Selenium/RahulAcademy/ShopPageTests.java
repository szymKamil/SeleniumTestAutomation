package Selenium.RahulAcademy;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.DataProviders.DataProviderLoginFormTest;
import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.Pages.ShopTest.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopLoginPageForm;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;
import Selenium.RahulAcademy.Base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


//@Listeners(TestNGListener.class)
public class ShopPageTests extends BaseTest {


    private static final Logger log = LoggerFactory.getLogger(ShopPageTests.class);
    ShopLoginPageForm pageShopLoginForm;
    ShopPage shopPage;
    CheckoutPage checkoutPage;

    @Test(dataProvider = "logInShopData", dataProviderClass = DataProviderLoginFormTest.class)
    public void loginFormVerification(String radio, String role){
        ShopLoginPageForm pageShopLoginForm = new ShopLoginPageForm(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        pageShopLoginForm.insertLogin().insertPassword().choseRadio(radio).selectForms(role)
                .selectTermsAndConditions()
                .clickSignIn();
    }

    @Test()
    public void verifyShopIsOnline(){
        pageShopLoginForm = new ShopLoginPageForm(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        shopPage = new ShopPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        shopPage.logInToAppAs("Admin", "Student").verifyShopIsOnline().verifySlider();
    }

    @Test
    public void verifySlider(){
        pageShopLoginForm = new ShopLoginPageForm(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        shopPage = new ShopPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        pageShopLoginForm = new ShopLoginPageForm(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        shopPage.logInToAppAs("Admin", "Student").verifySlider();
    }

    @Test
    public void shoppingCartTest(){
        checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        shopPage = new ShopPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result);
    }

    @Test
    public void shoppingCartRemoveFromCartTest(){
        checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        shopPage = new ShopPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result).removeProductsFromCart();
    }


    @Test
    public void shoppingCartFinaliseOrderTest(){
        shopPage = new ShopPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        //TODO: poprawić usatwienia przeglądarki by ignorowac komunikat o wykradzionym haśle
        CartPickResult result = shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result).goToCheckout().finaliseOrder("Lublin");
    }



}
