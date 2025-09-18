package POM.WebTest.SwagLabs.Tests;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.SwagLabs.Pages.CartPage;
import POM.WebTest.SwagLabs.Pages.LoginPage;
import org.testng.annotations.Test;

import java.time.Duration;


public class Tests extends BaseTest{


	LoginPage loginPage;
	CartPage cartPage;

	@Test
	void loginTest(){
		loginPage = new LoginPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		var login = loginPage.getLogin();
		logger.info("Pobrałem login: {}", login);
		var pass = loginPage.getPass();
		logger.info("Pobrałem hasło: {}", pass);
		loginPage.logInToApp(login, pass);
		logger.info("Poprawnie zalogowałem się do aplikacji");
	}


	@Test
	void openBurgerMenu(){
		loginTest();
		cartPage = new CartPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		cartPage.openHamburgerMenu();
		cartPage.pickMenuElement("Reset App State");
	}

	@Test
	void addChosenProductsToCart(){
		loginTest();
		cartPage = new CartPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		cartPage.pickCartElementAndAddToCart("Backpack");
		cartPage.pickCartElementsAndAddToCart("Sauce Labs Bike Light", "Sauce Labs Onesie", "Sauce Labs Bolt T-Shirt");
	}










}
