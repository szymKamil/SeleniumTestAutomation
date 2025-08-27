package POM.WebTest.RahulAcademy.Tests;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.MainPageShop;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class VegetableShopTests extends BaseTest{


	@Test
	public void searchTest(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.searchForProduct("Beans");
		assertThat(shopPage.getVisibleProducts().contains("Beans")).isTrue();
	}

	@Test
	public void pickNumberOfProduct(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
	}

	@DataProvider
	public Object[][] cartData(){
		return new Object[][]{
				{Map.of("Carrot", 4, "Apple", 6, "Grapes", 10)},
				{Map.of("Orange", 4, "Almonds", 3, "Water Melon", 3)}
		};
	}


	public void checkCartItems(){
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts("Carrot", 10);
		shopPage.pickAmountOfProducts("Tomato", 5);
		shopPage.pickAmountOfProducts("Pumpkin", 1);
		shopPage.pickAmountOfProducts("Apple", 5);
		shopPage.getCartItems().forEach((k, v) -> logger.info("W koszyku znajduje się: {}, w ilości {}", k , v));
	}



	@Test(dataProvider = "cartData")
	public void verifyCartDataProvider(Map<String,Integer> products) {
		MainPageShop shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		shopPage.pickAmountOfProducts(products);
		var cartItems = shopPage.getCartItems();
		shopPage.verifyCart(cartItems, products);

	}

}
