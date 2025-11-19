package POM.WebTest.RahulAcademy.Pages.ShopTest;

import Base.Drivers.DriverFactory;
import Base.Utils.RandomUtils;
import POM.WebTest.RahulAcademy.Helpers.CartPickResult;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopPage extends ShopLoginPageForm {

    WebElementActions actions;
    ShopLoginPageForm loginForm;

    public ShopPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        actions = new WebElementActions(DriverFactory.getDriver(), DriverFactory.getWait());
        loginForm = new ShopLoginPageForm(DriverFactory.getDriver(), DriverFactory.getWait());
    }


    //Elementy
    By appShop = By.xpath("//app-shop");
    By carouselSlider = By.id("carouselExampleIndicators");
    By carouselSliderNext = By.cssSelector("a.carousel-control-next");
    By carouselSliderList = By.cssSelector("ol.carousel-indicators");
    By productCard = By.cssSelector("app-card.col-lg-3");
    By productCardAddBtn = By.cssSelector("    app-card div div.card-footer button");
    By checkoutBtn = By.cssSelector("a.nav-link.btn.btn-primary");

    public ShopPage logInToAppAs(String userType, String userRole){
        loginForm.insertLogin().insertPassword().choseRadio(userType).selectForms(userRole).selectTermsAndConditions().clickSignIn();
        return this;
    }

    public ShopPage verifyShopIsOnline(){
        actions.find(appShop);
        return this;
    }

    public ShopPage verifySlider(){
    actions.find(carouselSlider).isVisible();
    String className;
    do{
         actions.find(carouselSliderNext).click();
         className = actions.findAll(carouselSliderNext).getFirst().classGetter();
     } while (className.equals("active"));
     return this;
    }

    public CartPickResult pickRandomProducts(){
        int numberOfProducts = RandomUtils.getRandomInt(25, 30);
        Map<String, Integer> pickedProducts = new HashMap<>();

        for (int i = 1; i < numberOfProducts; i++) {
            int productToPick = RandomUtils.getRandomInt(0, 4);
            actions.findAll(productCardAddBtn)
                    .get(productToPick).click();
            log.info("Wybieram produkt numer {}", i);
            switch (productToPick){
                case 0 -> pickedProducts.merge("iphone X", 1, Integer::sum);
                case 1 -> pickedProducts.merge("Samsung Note 8", 1, Integer::sum);
                case 2 -> pickedProducts.merge("Nokia Edge", 1, Integer::sum);
                case 3 -> pickedProducts.merge("Blackberry", 1, Integer::sum);
            }
        }
        assertThat(actions.find(checkoutBtn).getText().contains(Integer.toString(numberOfProducts - 1))).isTrue();
        return new CartPickResult(this, pickedProducts);
    }

    public CheckoutPage goToCheckout(){
        actions.find(checkoutBtn).click();
        return new CheckoutPage(DriverFactory.getDriver(), DriverFactory.getWait());
    }

}
