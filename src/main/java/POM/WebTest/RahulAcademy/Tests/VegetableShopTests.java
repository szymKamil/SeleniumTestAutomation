package POM.WebTest.RahulAcademy.Tests;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.MainPageShop;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.OrderConfirmationPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class VegetableShopTests extends BaseTest{


	@Test(groups = {"regresion", "interface"})
	public void searchTest(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.searchForProduct("Beans");
		assertThat(shopPage.getVisibleProducts().contains("Beans")).isTrue();
	}

	@Test(groups = {"interface"})
	public void pickNumberOfProduct(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
	}

	@Test(groups = {"business", "regression"})
	public void checkCartItems(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
		shopPage.pickAmountOfProducts("Tomato", 5);
		shopPage.pickAmountOfProducts("Pumpkin", 1);
		shopPage.pickAmountOfProducts("Apple", 5);
		shopPage.getCartItems().forEach((k, v) -> logger.info("W koszyku znajduje się: {}, w ilości {}", k , v));
	}

	@DataProvider
	public Object[][] cartData(){
		return new Object[][]{
				{Map.of("Carrot", 4, "Apple", 6, "Grapes", 10)},
				{Map.of("Orange", 4, "Almonds", 3, "Water Melon", 3, "Apple", 10)},
				{Map.of("Beetroot ", 15, "Beans", 4, "Onion", 15)},
				{Map.of("Mango", 1, "Nuts Mixture", 2, "Brocolli", 7)},
				{Map.of("Orange", 50, "Raspberry", 8, "Capsicum", 8)}
		};
	}


	@Test(dataProvider = "cartData")
	public void verifyCartDataProvider(Map<String,Integer> products) {
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts(products);
		var cartItems = shopPage.getCartItems();
		shopPage.verifyCart(cartItems, products);

	}

	@Test(dataProvider = "cartData", groups = "regression")
	public void checkoutVerification(Map<String,Integer> products) {
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		CheckoutPage checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts(products);
		shopPage.goToCheckout();
		checkoutPage.verifyTotalPrices();
	}


	@Test(groups = {"business", "regression"})
	public void discountVerificationTest(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		CheckoutPage checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
		shopPage.goToCheckout();
		checkoutPage.verifyDiscount();
	}

	@Test(groups = {"business"}, dataProvider = "cartData")
	public void priceAfterDiscountVerification(Map<String,Integer> products){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		CheckoutPage checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts(products);
		shopPage.goToCheckout();
		checkoutPage.verifyFullPriceAfterDiscount();
	}

	@Test(dataProvider = "cartData")
	public void placeOrderTest(Map<String,Integer> products) {
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		CheckoutPage checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts(products);
		shopPage.goToCheckout();
		checkoutPage.placeOrder();
	}

	@Test(groups = "regression")
	public void shoppingProcessTest() {
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		CheckoutPage checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
		shopPage.goToCheckout();
		checkoutPage.placeOrder();
		orderConfirmationPage.selectCounty();
		orderConfirmationPage.applyOrder();
	}

	@Test(groups = "business")
	public void verificationOfTermsAndConditions() {
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		CheckoutPage checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
		shopPage.goToCheckout();
		checkoutPage.placeOrder();
		orderConfirmationPage.selectCounty();
		orderConfirmationPage.verifyTermsAndConditionsInfo();
	}

}
