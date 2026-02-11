package POM.WebTest.RahulAcademy.Pages.VegetablesShop;

import Base.Utils.PageLoadedVerification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
	By confirmationText = By.xpath("//div[@class='wrapperTwo']/span");

	private static final String TERMS_TEXT = "Here the terms and condition page Click to geo back Home";
	private static final String CONFIRMATION_TEXT = "Thank you, your order has been placed successfully\nYou'll be redirected to Home page shortly!!";


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
		var confirmText = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationText)).getText();
		Assert.assertEquals(confirmText, CONFIRMATION_TEXT);
	}

	public void verifyTermsAndConditionsInfo(){
		wait.until(ExpectedConditions.elementToBeClickable(termsAndConditions)).click();
		List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
		String currentWindowHandle = driver.getWindowHandle();
		for (String handle : windowHandles){
			if (!handle.equals(currentWindowHandle)){
				driver.switchTo().window(handle);
				break;
			}
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(termsAndConditionsInfo));
		String tAndC = driver.findElement(termsAndConditionsInfo).getText();
		Assert.assertEquals(tAndC, TERMS_TEXT);
		driver.close();
	}




}
