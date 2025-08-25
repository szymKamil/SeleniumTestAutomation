package POM.WebTest.RahulAcademy.Tests;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.DataPrividers.DataProviderLoginFormTest;
import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.Listener.TestNGListener;
import POM.WebTest.RahulAcademy.Pages.ShopTest.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopLoginPageForm;
import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;


@Listeners(TestNGListener.class)
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
        shopPage.logInToAppAs("Admin", "Student").verifySlider();
    }

    @Test
    public void shoppingCartTest(){
        checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result);
    }

    @Test
    public void shoppingCartRemoveFromCartTest(){
        checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result).removeProductsFromCart();
    }


    @Test
    public void shoppingCartFinaliseOrderTest(){
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result).goToCheckout().finaliseOrder("Lublin");
    }



}
