package Cucumber.Rahul.VegetablesShop;

import Base.BaseTest.DriverFactoryV1;

import POM.WebTest.RahulAcademy.Pages.VegetablesShop.CheckoutPage;
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
	CheckoutPage checkoutPage;

	Map<String, Integer> mapOfProducts;

	@Before()
	public void setUp() throws InterruptedException {
		DriverFactoryV1.initDriver("chrome", 15);
		driver = DriverFactoryV1.getDriver();
		wait = DriverFactoryV1.getWait();
		shopPage = new MainPageShop(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		checkoutPage = new CheckoutPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
	}


	//Scenariusz 1: Użytkownik korzysta z wyszukiwarki w celu szybszego odnalezienia produktu
	@When("użytkownik skorzystał z wyszukiwarki sklepu i wpisał do niej nazwę {string}")
	public void searchSingleProduct(String product) {
		shopPage.searchForProduct(product);
	}

	//Metoda używana też w 2 scenariuszu
	@Then("tylko wyszukiwany produkt jest widoczny na liście")
	public void numOfProdsVerification(){
		shopPage.numOfProdVerification();
	}


	//Scenariusz 2: Użytkownik korzysta z wyszukiwarki w celu szybszego odnalezienia kilku produktów
	@When("użytkownik skorzystał z wyszukiwarki sklepu i wpisał do niej produkty:")
	public void searchMultipleProducts(DataTable productsTable) {
		List<String> products = productsTable.asList();
		shopPage.searchForProduct(products.toArray(new String[0]));
	}

	//Scenariusz 3/4: Dodanie produktu i wyświetlenie go w koszyku
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


	//Scenariusz 4: Użytkownik korzysta z kodu rabatowego w celu obniżenia kosztu zakupów
	@When("użytkownik przechodzi do podsumowania zamówienia")
	public void goToCheckout(){
		shopPage.goToCheckout();
	}

	@When("użytkownik korzysta z kodu rabatowego {string}, który obniża cenę jego zamówienia")
	public void verifyCart(String discountCode){
		checkoutPage.verifyDiscount(discountCode);
	}

	@Then("cena zamówienia zostaje obniżona o {string}")
	public void verifyDiscount(String discountPercentage){
		checkoutPage.verifyDiscountInfo(discountPercentage);
	}

	//Scenariusz 5:



	@After
	public void tearDown(){
		driver.quit();
	}



}
