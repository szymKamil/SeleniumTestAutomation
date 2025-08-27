package POM.WebTest.RahulAcademy.Pages.VegetablesShop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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





}
