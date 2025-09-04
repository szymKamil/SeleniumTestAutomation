package POM.WebTest.RahulAcademy.Pages.VegetablesShop;


import io.cucumber.java.it.Ma;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

	By numberOfItemsInCart = By.xpath("//b[text()='No. of Items     : ']/parent::*");
	By totalPriceInCart = By.cssSelector("span.totAmt");
	By totalPriceInCartAfterDiscount = By.xpath("//*[@id='root']/div/div/div/div/span[@class='discountAmt']");



	List<String> getProductsNamesFromCheckout(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(productNames));
		return	driver.findElements(productNames).stream().map(e -> e.getText().split(" - ")[0]).toList();
	}

	List<Integer> getProductsQuantityFromCheckout(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(productQuantities));
		return driver.findElements(productQuantities).stream().map(e -> Integer.parseInt(e.getText())).toList();

	}

	List<Integer> getProductsPriceFromCheckout(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
		return driver.findElements(productPrice).stream().map(e -> Integer.parseInt(e.getText())).toList();

	}

	List<Integer> getProductsTotalPriceFromCheckout(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrices));
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
		List<Integer> calculatedPrices = IntStream.range(0, productPrices.size()).mapToObj(i -> productPrices.get(i) * productQuantity.get(i)).toList();
		var productNamesList = getProductsNamesFromCheckout();
		if (calculatedPrices.equals(getProductsTotalPriceFromCheckout())){
			logger.info("Ceny się zgadzają.");
		} else {
			logger.info("Błędy w naliczaniu cen na podstawie ilości i ceny produktu.");
			calculatedPrices.forEach(e -> logger.error(e.toString()));
		}
		for (int i = 0; i < productNamesList.size(); i++){
			logger.info("W koszyku znajduje się produkt {} w ilości {}. Cena za sztukę wynosi {}, a całkowita cena wynosi {}", productNamesList.get(i), productQuantity.get(i), productPrices.get(i), calculatedPrices.get(i));
		}
	}

	public void verifyDiscount(String discountCode){
		WebElement promoIn = wait.until(ExpectedConditions.elementToBeClickable(promoInput));
		promoIn.clear();
		promoIn.sendKeys(discountCode);
		wait.until(ExpectedConditions.elementToBeClickable(applyPromoBtn)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(promoBtnSpinner));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(promoBtnSpinner));
		verifyDiscountInfo("10%");
	}

	public void verifyDiscountInfo(String expectedDiscount){
		String discountPercentage = driver.findElement(discountInfo).getText();
		Assert.assertEquals(discountPercentage, expectedDiscount, "W koszyku nie został naliczony poprawnie rabat po użyciu kodu rabatowego!");
	}




	public void verifyDiscount(){
		verifyDiscount(PROMO_CODE);
	}


	public boolean verifyFullPriceAfterDiscount(){
		var productPrices = getProductsTotalPriceFromCheckout();
		int productSize = productPrices.size();
		String totalPrice =  productPrices.stream().reduce( Integer::sum).map(Objects::toString).orElse("Błąd");
		wait.until(ExpectedConditions.visibilityOfElementLocated(numberOfItemsInCart)).getText().trim().split(" : ")[1].contains(Integer.toString(productSize));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(totalPriceInCart, totalPrice));
		verifyDiscount();
		String priceAfterDiscount = String.valueOf((Double.parseDouble(totalPrice) * 0.9));
		return driver.findElement(totalPriceInCartAfterDiscount).getText().contains(priceAfterDiscount);
	}

	public void placeOrder(){
		wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
	}



}
