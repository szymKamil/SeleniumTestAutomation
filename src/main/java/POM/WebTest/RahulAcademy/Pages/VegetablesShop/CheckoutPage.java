package POM.WebTest.RahulAcademy.Pages.VegetablesShop;

import io.cucumber.java.sl.In;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
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
	By productPrice = By.xpath("//*[@id='productCartTables']/tbody/tr/td[4]/p");
	By totalPrices = By.xpath("//*[@id='productCartTables']/tbody/tr/td[5]/p");


	By promoInput = By.cssSelector("input.promoCode");
	By applyPromoBtn = By.cssSelector("button.promoBtn");
	By discountInfo = By.cssSelector("span.discountPerc");
	By promoBtnSpinner = By.cssSelector("span.promo-btn-loader");
	By placeOrderBtn = By.xpath("//button[text() = 'Place Order']");


	public void checker(){
		wait.until(ExpectedConditions.elementToBeClickable(promoInput));
		var list = driver.findElements(productNames).stream().map(e -> e.getText().split(" - ")[0]).toList();
		logger.info(String.valueOf(list));


	}


	List<String> getProductsNamesFromCheckout(){
		return driver.findElements(productNames).stream().map(e -> e.getText().split(" - ")[0]).toList();
	}

	List<Integer> getProductsQuantityFromCheckout(){
		return driver.findElements(productQuantities).stream().map(e -> Integer.parseInt(e.getText())).toList();

	}

	List<Integer> getProductsPriceFromCheckout(){
		return driver.findElements(productPrice).stream().map(e -> Integer.parseInt(e.getText())).toList();

	}

	List<Integer> getProductsTotalPriceFromCheckout(){
		return driver.findElements(totalPrices).stream().map(e -> Integer.parseInt(e.getText())).toList();
	}


	public Map<String, Integer> getItemsNamesAndQuantity(){
		List<Integer> productQuantity = getProductsQuantityFromCheckout();
		List<String> productNamesList = getProductsNamesFromCheckout();
		return  IntStream.range(0, productNamesList.size()).boxed().collect(Collectors.toMap(
				productNamesList::get,
				productQuantity::get
		));
	}


	public void verifyTotalPrices() {
		var productQuantity = getProductsQuantityFromCheckout();
		var productPrices = getProductsPriceFromCheckout();
		System.out.println(productQuantity);
		System.out.println(productPrices);


		List<Integer> calculatedPrices = IntStream.range(0, productPrices.size()).mapToObj(i -> productPrices.get(i) * productQuantity.get(i)).toList();
		calculatedPrices.forEach(e -> logger.info("Cena produktu wynosi {}", e));
		if (calculatedPrices.equals(getProductsTotalPriceFromCheckout())){
			logger.info("Ceny się zgadzają.");
		} else {
			logger.info("Błędy w naliczaniu cen na podstawie ilości i ceny produktu.");
			calculatedPrices.forEach(e -> logger.error(e.toString()));
		}
		calculatedPrices.forEach(e -> logger.info("Cena produktu wynosi {}", e));

	}


}
