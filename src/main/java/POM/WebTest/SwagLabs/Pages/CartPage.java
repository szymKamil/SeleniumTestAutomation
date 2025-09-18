package POM.WebTest.SwagLabs.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {

	WebDriver driver;
	WebDriverWait wait;

	public CartPage(WebDriver driver, WebDriverWait wait) {
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
	WebElement inventoryProductName;

	@FindBy(css = "button.btn_inventory")
	WebElement addToCartBtn;

	@FindBy(css = "select.product_sort_container")
	WebElement selectFilterBtn;


	//Metody
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

	public void pickCartElementAndAddToCart(String product){
		wait.until(ExpectedConditions.visibilityOfAllElements(inventoryProduct));
		inventoryProduct.stream().filter(e -> e.getText().contains(product))
				.findFirst()
				.get()
				.findElement(By.cssSelector("button.btn_inventory"))
				.click();
	}

	public void pickCartElementsAndAddToCart(String... products){
		for (String product : products){
			pickCartElementAndAddToCart(product);
		}
	}



}
