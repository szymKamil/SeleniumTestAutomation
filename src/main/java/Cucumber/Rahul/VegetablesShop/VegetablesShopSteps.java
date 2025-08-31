package Cucumber.Rahul.VegetablesShop;

import Base.BaseTest.DriverFactoryV1;

import POM.WebTest.RahulAcademy.Pages.VegetablesShop.MainPageShop;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Map;


public class VegetablesShopSteps {

	WebDriver driver;
	WebDriverWait wait;

	MainPageShop shopPage;

	Map<String, Integer> mapOfProducts;

	@Before()
	public void setUp() throws InterruptedException {
		DriverFactoryV1.initDriver("chrome", 15);
		driver = DriverFactoryV1.getDriver();
		wait = DriverFactoryV1.getWait();
		shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
	}



	@When("użytkownik skorzystał z wyszukiwarki sklepu i wpisał do niej nazwę {string}")
	public void searchSingleProduct(String product) {
		shopPage.searchForProduct(product);
	}

	@When("użytkownik skorzystał z wyszukiwarki sklepu i wpisał do niej produkty:")
	public void searchMultipleProducts(DataTable productsTable) {
		List<String> products = productsTable.asList();
		shopPage.searchForProduct(products.toArray(new String[0]));
	}

	@Given("użytkownik dodał do koszyka następujące produkty:")
	public void addMultipleProductsToCart(DataTable table)  {
		mapOfProducts = table.asMap(String.class, Integer.class);
		shopPage.pickAmountOfProducts(mapOfProducts);
	}

	@When("użytkownik otwiera koszyk")
	public void displayCart(){
		shopPage.openCart();
	}

	@Then("widoczne są w nim dodane produkty")
	public void verifyCart(){
		var prodInCart = shopPage.getCartItems();
		System.out.println(prodInCart);
		System.out.println(mapOfProducts);
		var bool = shopPage.verifyCart(prodInCart,mapOfProducts);
		System.out.println(bool);
	}


	@Then("tylko wyszukiwany produkt jest widoczny na liście")
	public void numOfProdsVerification(){
		shopPage.numOfProdVerification();
	}

	@After
	public void tearDown(){
		driver.quit();
	}



}
