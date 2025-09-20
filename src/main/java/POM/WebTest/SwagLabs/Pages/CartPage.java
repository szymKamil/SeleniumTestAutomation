package POM.WebTest.SwagLabs.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartPage {


	WebDriver driver;
	WebDriverWait wait;

	public CartPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "div.cart_item")
	List<WebElement> productsInCartList;

	@FindBy(css = "div.inventory_item_name")
	List<WebElement> productsInCartListNames;

	//Metody

	public int verifyNumberOfCartProducts(){
		return  wait.until(ExpectedConditions.visibilityOfAllElements(productsInCartList)).size();
	}

	public boolean verifyProductsInCart(String... products){
		return Arrays.stream(products)
				.allMatch(prod ->
						productsInCartListNames.stream()
								.anyMatch(e -> e.getText().contains(prod))
				);
	}



}
