package POM.WebTest.RahulAcademy.Pages.VegetablesShop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderConfirmationPage {


	WebDriver driver;
	WebDriverWait wait;
	Logger logger = LoggerFactory.getLogger("OrderConfirmationLogger");


	public OrderConfirmationPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	//Elementy
	By countySelect = By.xpath("//div/select");
	By termsAndConditions = By.xpath("//a[text() = 'Terms & Conditions']");
	By termsAgreeCheckbox = By.cssSelector("input[type=checkbox]");
	By proceedBtn = By.xpath("//button[text() = 'Proceed']");
	By termsAndConditionsInfo = By.cssSelector("div.wrapperTwo");


	private static final String TERMS_TEXT = "Here the terms and condition page Click to geo back";

	public void selectCounty(String country){
		wait.until(ExpectedConditions.visibilityOfElementLocated(countySelect));
		Select select = new Select(driver.findElement(countySelect));
		select.selectByValue(country);
	}

	public void selectCounty(){
		selectCounty("Poland");
	}

	public void applyOrder(){
		wait.until(ExpectedConditions.elementToBeClickable(termsAgreeCheckbox)).click();
		wait.until(ExpectedConditions.elementToBeClickable(proceedBtn)).click();
	}

	public void verifyTermsAndConditionsInfo(){
		wait.until(ExpectedConditions.textToBePresentInElementLocated(termsAndConditionsInfo, TERMS_TEXT));
		driver.close();
	}




}
