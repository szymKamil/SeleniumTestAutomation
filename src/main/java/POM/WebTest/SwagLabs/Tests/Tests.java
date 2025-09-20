package POM.WebTest.SwagLabs.Tests;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.SwagLabs.Pages.CartPage;
import POM.WebTest.SwagLabs.Pages.InventoryPage;
import POM.WebTest.SwagLabs.Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Tests extends BaseTest{


	LoginPage loginPage;
	InventoryPage inventoryPage;
	CartPage cartPage;

	@Test
	void loginTest(){
		loginPage = new LoginPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		loginPage.logInToApp("standard_user", "secret_sauce");
		logger.info("Poprawnie zalogowałem się do aplikacji");
	}

	@Test
	void openBurgerMenu(){
		loginTest();
		inventoryPage = new InventoryPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		inventoryPage.openHamburgerMenu();
		inventoryPage.pickMenuElement("Reset App State");
		logger.info("Zresetowany został stan aplikacji.");
	}

	@Test
	void addChosenProductsToCartAndVerifyCart(){
		loginTest();
		inventoryPage = new InventoryPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		inventoryPage.pickCartElementsAndAddToCart("Sauce Labs Bike Light", "Sauce Labs Onesie", "Sauce Labs Bolt T-Shirt");
		inventoryPage.pickCartElementAndAddToCart("Fleece Jacket");
		inventoryPage.openProductCard("Backpack");
		inventoryPage.addProductInCard();
		inventoryPage.closeProductCard();
		inventoryPage.openShoppingCart();
		int numOfProdsInInventory = 5;
		logger.info("Dodanych zostało w sumie {} produktów do koszyka", numOfProdsInInventory);
		inventoryPage.verifyNumberOfProductsAddedToCartInTag(numOfProdsInInventory);
		cartPage = new CartPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		var numOfProds = cartPage.verifyNumberOfCartProducts();
		logger.info("W koszyku znajduje się {} produktów.", numOfProds);
		Assert.assertEquals(numOfProds, numOfProdsInInventory);
		cartPage.verifyProductsInCart("Sauce Labs Bike Light", "Sauce Labs Onesie", "Sauce Labs Bolt T-Shirt", "Fleece Jacket" , "Backpack");
		logger.info("Wszystkie produkty znajdują się w koszyku.");

	}










}
