package POM.WebTest.RahulAcademy.Pages.VegetablesShop;

import io.cucumber.java.sl.In;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CheckoutPage {

	Logger logger = LoggerFactory.getLogger("CheckoutPageLogger");
	WebDriver driver;
	WebDriverWait wait;

	public CheckoutPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	private static final String PROMO_CODE = "rahulshettyacademy";

	By productNames = By.cssSelector("td p.product-name");
	By productQuantities = By.cssSelector("td p.quantity");


	By promoInput = By.cssSelector("input.promoCode");
	By applyPromoBtn = By.cssSelector("button.promoBtn");
	By discountInfo = By.cssSelector("span.discountPerc");
	By promoBtnSpinner = By.cssSelector("span.promo-btn-loader");
	By placeOrderBtn = By.xpath("//button[text() = 'Place Order']");


	public List<String> getItemsFromCheckout(){
		return driver.findElements(productNames).stream().map(e -> e.getText().split(" - ")[0]).toList();
	}

	public Map<String, Integer> getItemsQuantity(){
		List<Integer> productQuantity = driver.findElements(productQuantities).stream().map(e -> Integer.parseInt(e.getText())).toList();
		List<String> productNames = getItemsFromCheckout();
		return  IntStream.range(0, productNames.size()).boxed().collect(Collectors.toMap(
				productNames::get,
				productQuantity::get
		));
	}



}
