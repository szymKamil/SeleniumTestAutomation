package POM.WebTest.RahulAcademy.Tests;

import POM.WebTest.RahulAcademy.DataPrividers.DataProviderLoginFormTest;
import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.Listener.TestNGListener;
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

    @Test(dataProvider = "logInShopData", dataProviderClass = DataProviderLoginFormTest.class)
    public void loginFormVerification(String radio, String role){
        pageShopLoginForm.insertLogin().insertPassword().choseRadio(radio).selectForms(role)
                .selectTermsAndConditions()
                .clickSignIn();
    }

    @Test()
    public void verifyShopIsOnline(){
        shopPage.logInToAppAs("Admin", "Student").verifyShopIsOnline().verifySlider();
    }

    @Test
    public void verifySlider(){
        shopPage.logInToAppAs("Admin", "Student").verifySlider();
    }

    @Test
    public void shoppingCartTest(){
        CartPickResult result =  shopPage.logInToAppAs("Admin", "Student").pickRandomProducts();
        shopPage.goToCheckout();
        checkoutPage.verifyNumberOfProducts(result);
    }

    @Test
    public void shoppingCartRemoveFromCartTest(){
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
