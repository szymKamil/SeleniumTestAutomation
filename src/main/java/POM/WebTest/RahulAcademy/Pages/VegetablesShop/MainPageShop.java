package POM.WebTest.RahulAcademy.Pages.VegetablesShop;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPageShop {


	Logger logger = LoggerFactory.getLogger("VegetableShopLogger");
	WebDriver driver;
	WebDriverWait wait;

	public MainPageShop(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	//Elementy
	By cartIcon = By.cssSelector("a.cart-icon");
	By productCard = By.cssSelector("div.product");
	By productName = By.cssSelector("h4.product-name");
	By decrementProduct = By.cssSelector("a.decrement");
	By incrementProduct = By.cssSelector("a.increment");
	By searchInput = By.cssSelector("input[type='search']");


	//Metody






}
