package POM.WebTest.RahulAcademy.Pages.VegetablesShop;


import Base.BaseTest.DriverFactoryV1;
import Base.Utils.JavaScriptUtils;
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
import org.testng.Assert;

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
	By productCard = By.cssSelector("div.products div.product");
	By productName = By.cssSelector("h4.product-name");
	By decrementProduct = By.cssSelector("a.decrement");
	By incrementProduct = By.cssSelector("a.increment");
	By searchInput = By.cssSelector("input[type='search']");
	By productQuantity = By.cssSelector("input.quantity");
	By addToCartBtn = By.cssSelector("button[type='button']");

	By cart = By.cssSelector("div.cart-preview");
	By cartItems = By.cssSelector("div.cart-preview div div ul.cart-items");
	By cartProducts = By.cssSelector("li.cart-item");
	By cartProductsNames = By.xpath("//div[@class='cart-preview active']/div/div/ul/li[@class='cart-item']/..//p[@class='product-name']");
	By cartProductsQuantity = By.xpath("//div[@class='cart-preview active']/div/div/ul/li[@class='cart-item']/..//p[@class='quantity']");
	By cartCheckoutBtn = By.cssSelector("div.cart div.action-block button[type='button']");




	//Metody
	public void searchForProduct(String product) {
		WebElement searchInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
		searchInputField.clear();
		searchInputField.sendKeys(product);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(productName, product));
	}

	public void numOfProdVerification(){
		wait.until(ExpectedConditions.numberOfElementsToBe(productCard, 1));
		Assert.assertEquals(driver.findElements(productCard).size(), 1);
	}

	public void pickAmountOfProducts(String product, int amount) {
			List<WebElement> productElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCard));
			Optional<WebElement> filteredProduct = productElements.stream()
					.filter(e -> !e.findElement(productName)
							.getText().isBlank()).filter(e -> e.findElement(productName).getText().contains(product))
					.findFirst();
			if (filteredProduct.isPresent()) {
				if (amount > 4){
					WebElement el = filteredProduct.get().findElement(productQuantity);
					el.clear();
					el.sendKeys(String.valueOf(amount));
				} else {
					int currentValue = Integer.parseInt(Objects.requireNonNull(filteredProduct.get()
							.findElement(productQuantity)
							.getAttribute("value")));
					if (currentValue < amount) {
						while (currentValue != amount) {
							var incrementBtn = wait.until(ExpectedConditions.elementToBeClickable(filteredProduct.get()
									.findElement(incrementProduct)));
							JavaScriptUtils.scrollToElementJS(DriverFactoryV1.getDriver(), incrementBtn);
							incrementBtn.click();
							currentValue++;
						}
					} else if (currentValue > amount) {
						while (currentValue != amount) {
							var decrementtBtn = wait.until(ExpectedConditions.elementToBeClickable(filteredProduct.get()
									.findElement(decrementProduct)));
							JavaScriptUtils.scrollToElementJS(DriverFactoryV1.getDriver(), decrementtBtn);
							decrementtBtn.click();
							currentValue--;
						}
					} else if (currentValue == amount) {
						// pusto
					} else {
						logger.error("Błąd podczas wybierania produktu {}", filteredProduct.get()
								.getText());
					}
				}
			}
			filteredProduct.ifPresent(this::addElementToCart);
	}

	public void pickAmountOfProducts(Map<String, Integer> map) {
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			logger.info("Wybieram produkt {} ilość {}", entry.getKey(), entry.getValue());
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

	public Map<String, Integer> getCartItems(){
		wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
		List<String> cartItemsNames = driver.findElements(cartProductsNames).stream().map(e -> e.getText().split(" - ")[0]).toList();
		List<String> cartItemsQuantity = driver.findElements(cartProductsQuantity).stream().map(e -> e.getText().split(" ")[0]).toList();
		Map<String, Integer> cartItems = new HashMap<>();
		for (int i = 0; i < cartItemsNames.size(); i++){
			cartItems.put(cartItemsNames.get(i), Integer.parseInt(cartItemsQuantity.get(i)));
		}
		return cartItems;
	}

	public boolean verifyCart(Map<String, Integer> cartItems, Map<String, Integer> testData){
		return testData.entrySet().stream()
				.allMatch(e -> {
					var actualQtyStr = cartItems.get(e.getValue());
					if (actualQtyStr == null) return false;
					try {
						return actualQtyStr == e.getValue();
					} catch (NumberFormatException ex) {
						return false;
					}
				});
	}

	public void goToCheckout(){
		if (!driver.findElement(cart).isDisplayed()){
			wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(cartCheckoutBtn)).click();

	}



}
