package POM.WebTest.SwagLabs.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;


public class CartPage {

	/***
	 * Koszyk z produktami strony Swag Labs. Inicjowana w teście poprzez konstruktor, parametryzowane driverem, waitem, a także dodatkowo uruchamiający PageFactory.
	 * Zawiera ona lokatory głównych elementów, takich jak elementy koszyka, elementy formularza zakupu czy komunikatu o finalizacji zamówienia.
	 * Dodatkowo posiada stringi komunikatów o sukcesie zakupu produktu, które weryfikują w teście, czy komunikaty są poprawne.
	 * Interakcje na tych elementach są obsługiwane poprzez metody, takie jak: weryfikacja produktów w koszyku, wypełnienie formularza zakupu,
	 * czy weryfikacja powiadomienia o pozytywnym procesie zakupu.
	 */

	WebDriver driver;
	WebDriverWait wait;

	public CartPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	protected final String SUCCESS_HEADER_INFO = "Thank you for your order!";
	protected final String SUCCESS_PARAGRAPH_INFO = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";


	@FindBy(css = "div.cart_item")
	List<WebElement> productsInCartList;

	@FindBy(css = "div.inventory_item_name")
	List<WebElement> productsInCartListNames;

	@FindBy(id = "checkout")
	WebElement checkoutBtn;

	@FindBy(id = "first-name")
	WebElement checkoutFormInputName;

	@FindBy(id = "last-name")
	WebElement checkoutFormInputSurname;

	@FindBy(id = "postal-code")
	WebElement checkoutFormInputPostalCode;

	@FindBy(id = "continue")
	WebElement continueBtn;

	@FindBy(id = "finish")
	WebElement finishOrderBtn;

	@FindBy(xpath = "//h2[@data-test='complete-header']")
	WebElement successInfoHeader;

	@FindBy(xpath = "//div[@data-test='complete-text']")
	WebElement successInfoParagraph;

	@FindBy(id = "back-to-products")
	WebElement backToProdsBtn;


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

	public void goToCheckout(){
		wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
	}

	public void fillForm(String name, String surname, String postalCode){
		wait.until(ExpectedConditions.visibilityOf(checkoutFormInputName)).sendKeys(name);
		wait.until(ExpectedConditions.visibilityOf(checkoutFormInputSurname)).sendKeys(surname);
		wait.until(ExpectedConditions.visibilityOf(checkoutFormInputPostalCode)).sendKeys(postalCode);
	}

	public void clickContinueBtn(){
		wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
	}

	public void finishOrderBtn(){
		wait.until(ExpectedConditions.elementToBeClickable(finishOrderBtn)).click();
	}

	public void verifySuccessOrderInfo(){
		var header = wait.until(ExpectedConditions.visibilityOf(successInfoHeader)).getText();
		var paragraph = wait.until(ExpectedConditions.visibilityOf(successInfoParagraph)).getText();
		Assert.assertEquals(header, SUCCESS_HEADER_INFO, "Test w headerze z informacją o pomyślnym zamówieniu nie zgadza się!");
		Assert.assertEquals(paragraph, SUCCESS_PARAGRAPH_INFO, "Test w paragrafie z informacją o pomyślnym zamówieniu nie zgadza się!");
	}

	public void backToProdsBtnClick(){
		wait.until(ExpectedConditions.elementToBeClickable(backToProdsBtn)).click();
	}

}
