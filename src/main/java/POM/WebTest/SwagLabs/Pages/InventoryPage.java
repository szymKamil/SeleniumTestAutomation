package POM.WebTest.SwagLabs.Pages;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.SwagLabs.Utils.InventoryFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class InventoryPage {

	/***
	 * Sklep z produktami strony Swag Labs. Inicjowana w teście poprzez konstruktor, parametryzowane driverem, waitem, a także dodatkowo uruchamiający PageFactory.
	 * Zawiera ona lokatory głównych elementów strony jak produkty, odwołania do hamburger menu, elementy przycisku filtrowania, elementy niezbędne w procesie zakupu
	 * (dodanie do koszyka, przejście do zamówienia), a także otwarcie karty produktu.
	 * Interakcje na tych elementach są obsługiwane poprzez metody, takie jak: otwarcie hamburger menu, wybranie jednego z przycisków menu, dodanie produktu do koszyka, otwarcie karty,
	 * dodanie/usunięcie produktu, czy metody związane z filtrowaniem (ustawienie filtra, weryfikacja zmiany kolejności elementów).
	 */


	WebDriver driver;
	WebDriverWait wait;

	public InventoryPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "react-burger-menu-btn")
	WebElement burgerMenuIcon;

	@FindBy(css = "div.bm-menu-wrap")
	WebElement burgerMenuDiv;

	@FindBy(css = "a.bm-item.menu-item")
	List<WebElement> burgerMenuElements;

	@FindBy(css = "div.inventory_item")
	List<WebElement> inventoryProduct;

	@FindBy(css = "div.inventory_item_name")
	List<WebElement> inventoryProductName;

	@FindBy(css = "div.inventory_item_price")
	List<WebElement> inventoryProductPrice;

	@FindBy(name = "back-to-products")
	WebElement backToProductsBtnCard;

	@FindBy(id = "add-to-cart")
	WebElement addToCartInCardBtn;

	@FindBy(id = "remove")
	WebElement removeFromCartInCardBtn;

	@FindBy(css = "button.btn_inventory")
	WebElement addToCartBtn;

	@FindBy(css = "select.product_sort_container")
	WebElement selectFilterBtn;

	@FindBy(css = "span.active_option")
	WebElement activeFilter;

	@FindBy(css = "a.shopping_cart_link")
	WebElement shoppingCartIcon;

	@FindBy(css = "span.shopping_cart_badge")
	WebElement shoppingCartIconAmountTag;



	//Metody
	//BurgerMenu
	public void openHamburgerMenu(){
		wait.until(ExpectedConditions.elementToBeClickable(burgerMenuIcon)).click();
		wait.until(ExpectedConditions.visibilityOf(burgerMenuDiv));
	}

	public void pickMenuElement(String burgerMenuElementName){
		if (!burgerMenuDiv.isDisplayed()){
			openHamburgerMenu();
		}
		wait.until(ExpectedConditions.visibilityOf(burgerMenuDiv));
		wait.until(ExpectedConditions.visibilityOfAllElements(burgerMenuElements));
		burgerMenuElements.stream().filter(e -> e.getText().contains(burgerMenuElementName)).findFirst().get().click();
	}

	//Filtrowanie
	public void changeFilter(InventoryFilter filter){
		wait.until(ExpectedConditions.visibilityOf(selectFilterBtn));
		Select select = new Select(selectFilterBtn);
		select.selectByVisibleText(filter.getValue());
	}

	public String getFilterValue(){
		wait.until(ExpectedConditions.visibilityOf(activeFilter));
		return activeFilter.getText();
	}

	public void verifyProductPrices(boolean asc){
		wait.until(ExpectedConditions.visibilityOfAllElements(inventoryProductPrice));
		List<Double> prices = inventoryProductPrice.stream().map(WebElement::getText)
				.map(text -> text.replace("$", ""))
				.map(Double::parseDouble)
				.toList();
		if (asc) {
			List<Double> sortedPricesAsc = new ArrayList<>(prices);
			sortedPricesAsc.sort(Double::compareTo);
			Assert.assertEquals(prices, sortedPricesAsc, "Ceny nie są posortowane!");
		} else {
			List<Double> sortedPricesDesc = new ArrayList<>(prices);
			sortedPricesDesc.sort(Comparator.reverseOrder());
			Assert.assertEquals(prices, sortedPricesDesc, "Ceny nie są posortowane!");
		}
	}

	//Proces zakupowy produktów
	public void pickCartElementAndAddToCart(String product){
		wait.until(ExpectedConditions.visibilityOfAllElements(inventoryProduct));
		var filteredProduct = inventoryProduct.stream().filter(e -> e.getText().contains(product))
				.findFirst();
		filteredProduct.get().findElement(By.cssSelector("button.btn_inventory")).click();
	}

	public void pickCartElementsAndAddToCart(String... products){
		for (String product : products){
			pickCartElementAndAddToCart(product);
		}
	}

	public void pickCartElementsAndAddToCart(Set<String> productsSet){
		for (String product : productsSet){
			pickCartElementAndAddToCart(product);
		}
	}

	public void openProductCard(String product){
		inventoryProductName.stream().filter(e -> e.getText().contains(product)).findFirst().ifPresent(WebElement::click);
	}

	public void closeProductCard(){
		wait.until(ExpectedConditions.visibilityOf(backToProductsBtnCard)).click();
	}

	public void addProductInCard(){
		wait.until(ExpectedConditions.visibilityOf(addToCartInCardBtn)).click();
	}

	public void removeProductFromCard(){
		wait.until(ExpectedConditions.visibilityOf(removeFromCartInCardBtn)).click();
	}

	public void verifyNumberOfProductsAddedToCartInTag(int amount){
		wait.until(ExpectedConditions.textToBePresentInElement(shoppingCartIconAmountTag, String.valueOf(amount)));

	}

	public void openShoppingCart(){
		wait.until(ExpectedConditions.visibilityOf(shoppingCartIcon)).click();
	}





}
