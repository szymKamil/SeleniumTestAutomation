package Selenium.SwagLabs;

import Base.Drivers.DriverFactory;
import POM.WebTest.SwagLabs.Pages.CartPage;
import POM.WebTest.SwagLabs.Pages.InventoryPage;
import POM.WebTest.SwagLabs.Pages.LoginPage;
import POM.WebTest.SwagLabs.Base.BaseTest;
import POM.WebTest.SwagLabs.Utils.InventoryFilter;
import POM.WebTest.SwagLabs.Utils.PickRandomProducts;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Tests extends BaseTest {


	LoginPage loginPage;
	InventoryPage inventoryPage;
	CartPage cartPage;

	@Test
	void loginTest(){
		loginPage = new LoginPage(DriverFactory.getDriver(), DriverFactory.getWait());
		loginPage.logInToApp("standard_user", "secret_sauce");
		logger.info("Poprawnie zalogowałem się do aplikacji");
	}

	@Test
	void openBurgerMenu(){
		loginTest();
		inventoryPage = new InventoryPage(DriverFactory.getDriver(), DriverFactory.getWait());
		inventoryPage.openHamburgerMenu();
		inventoryPage.pickMenuElement("Reset App State");
		logger.info("Zresetowany został stan aplikacji.");
	}

	@Test
	void filterTest(){
		loginTest();
		inventoryPage = new InventoryPage(DriverFactory.getDriver(), DriverFactory.getWait());
		inventoryPage.changeFilter(InventoryFilter.LowToHigh);
		var activerFilter = inventoryPage.getFilterValue();
		logger.info("Aktywny filtr to {}", activerFilter);
		Assert.assertEquals(activerFilter, InventoryFilter.LowToHigh.getValue());
		inventoryPage.verifyProductPrices(true);
		inventoryPage.changeFilter(InventoryFilter.ZtoA);
		activerFilter = inventoryPage.getFilterValue();
		logger.info("Aktywny filtr to {}", activerFilter);
		Assert.assertEquals(activerFilter, InventoryFilter.ZtoA.getValue());
		inventoryPage.changeFilter(InventoryFilter.HighToLow);
		activerFilter = inventoryPage.getFilterValue();
		logger.info("Aktywny filtr to {}", activerFilter);
		Assert.assertEquals(activerFilter, InventoryFilter.HighToLow.getValue());
		inventoryPage.verifyProductPrices(false);
	}

	@Test
	void addChosenProductsToCartAndVerifyCart(){
		loginTest();
		inventoryPage = new InventoryPage(DriverFactory.getDriver(), DriverFactory.getWait());
		inventoryPage.pickCartElementsAndAddToCart(PickRandomProducts.pickRandomProducts(4));
		inventoryPage.pickCartElementAndAddToCart("Fleece Jacket");
		inventoryPage.openProductCard("Backpack");
		inventoryPage.addProductInCard();
		inventoryPage.closeProductCard();
		inventoryPage.openShoppingCart();
		int numOfProdsInInventory = 5;
		logger.info("Dodanych zostało w sumie {} produktów do koszyka", numOfProdsInInventory);
		inventoryPage.verifyNumberOfProductsAddedToCartInTag(numOfProdsInInventory);
		cartPage = new CartPage(DriverFactory.getDriver(), DriverFactory.getWait());
		var numOfProds = cartPage.verifyNumberOfCartProducts();
		logger.info("W koszyku znajduje się {} produktów.", numOfProds);
		Assert.assertEquals(numOfProds, numOfProdsInInventory);
		cartPage.verifyProductsInCart("Sauce Labs Bike Light", "Sauce Labs Onesie", "Sauce Labs Bolt T-Shirt", "Fleece Jacket" , "Backpack");
		logger.info("Wszystkie produkty znajdują się w koszyku.");
	}


	@Test
	void buyingProductPathTest(){
		loginTest();
		inventoryPage = new InventoryPage(DriverFactory.getDriver(), DriverFactory.getWait());
		inventoryPage.pickCartElementsAndAddToCart("Sauce Labs Onesie", "Sauce Labs Fleece Jacket", "Sauce Labs Bolt T-Shirt");
		int numOfProdsInInventory = 3;
		inventoryPage.verifyNumberOfProductsAddedToCartInTag(numOfProdsInInventory);
		inventoryPage.openShoppingCart();
		cartPage = new CartPage(DriverFactory.getDriver(), DriverFactory.getWait());
		var numOfProds = cartPage.verifyNumberOfCartProducts();
		logger.info("W koszyku znajduje się {} produktów.", numOfProds);
		Assert.assertEquals(numOfProds, numOfProdsInInventory);
		cartPage.verifyProductsInCart("Sauce Labs Onesie", "Sauce Labs Fleece Jacket", "Sauce Labs Bolt T-Shirt");
		logger.info("Wszystkie produkty znajdują się w koszyku.");
		cartPage.goToCheckout();
		cartPage.fillForm("Jan", "Nowak", "00-001");
		cartPage.clickContinueBtn();
		cartPage.finishOrderBtn();
		cartPage.verifySuccessOrderInfo();
		cartPage.backToProdsBtnClick();
		Assert.assertEquals(DriverFactory.getDriver().getCurrentUrl() //TODO: Przenieść pobranie URL do jakiejś uniwersalnej akcji.
				, "https://www.saucedemo.com/inventory.html");
	}

}
