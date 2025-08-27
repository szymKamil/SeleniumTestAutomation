package POM.WebTest.RahulAcademy.Pages.VegetablesShop;


import Base.Utils.PageLoadedVerification;
import Base.Utils.Utils;
import io.cucumber.java.sl.In;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
	By productQuantity = By.cssSelector("input.quantity");
	By addToCartBtn = By.cssSelector("button[type='button']");

	By cartItems = By.cssSelector("div.cart-preview div div ul.cart-items");
	By cartProducts = By.cssSelector("li.cart-item");
	By cartProductsNames = By.xpath("//div[@class='cart-preview active']/div/div/ul/li[@class='cart-item']/..//p[@class='product-name']");
	By cartProductsQuantity = By.xpath("//div[@class='cart-preview active']/div/div/ul/li[@class='cart-item']/..//p[@class='quantity']");
	By cartCheckoutBtn = By.xpath("div.cart div.action-block button[type='button']");




	//Metody
	public void searchForProduct(String product) {
		WebElement searchInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
		searchInputField.clear();
		searchInputField.sendKeys(product);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(productName, product));
	}

	public void pickAmountOfProducts(String product, int amount) {
			List<WebElement> productElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCard));
			Optional<WebElement> filteredProduct = productElements.stream()
					.filter(e -> !e.findElement(productName)
							.getText().isBlank()).filter(e -> e.findElement(productName).getText().contains(product))
					.findFirst();
			if (filteredProduct.isPresent()) {
				int currentValue = Integer.parseInt(Objects.requireNonNull(filteredProduct.get()
						.findElement(productQuantity)
						.getAttribute("value")));
				if (currentValue < amount) {
					while (currentValue != amount) {
						filteredProduct.get().findElement(incrementProduct).click();
						currentValue++;
					}
				} else if (currentValue > amount) {
					while (currentValue != amount) {
						filteredProduct.get().findElement(decrementProduct).click();
						currentValue--;
					}
				}  else if (currentValue == amount) {
					// pusto
				}
				else {
					logger.error("Błąd podczas wybierania produktu {}", filteredProduct.get()
							.getText());
				}
			}
			filteredProduct.ifPresent(this::addElementToCart);
	}

	public void pickAmountOfProducts(Map<String, Integer> map) {
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			pickAmountOfProducts(entry.getKey(), entry.getValue());
		}
	}


	public List<String> getVisibleProducts(){
		List<WebElement> elements = wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(productName)
		);
		return elements.stream().map(e -> e.getText().split(" - ")[0]).toList();

	}

	public void addElementToCart(WebElement element){
		element.findElement(addToCartBtn).click();
	}

	public Map<String, String> verifyCart(){
		wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
		List<String> cartItemsNames = driver.findElements(cartProductsNames).stream().map(e -> e.getText().split(" - ")[0]).toList();
		List<String> cartItemsQuantity = driver.findElements(cartProductsQuantity).stream().map(e -> e.getText().split(" ")[0]).toList();
		Map<String, String> cartItems = new HashMap<>();
		for (int i = 0; i < cartItemsNames.size(); i++){
			cartItems.put(cartItemsNames.get(i), cartItemsQuantity.get(i));
		}
		return cartItems;
	}


}
