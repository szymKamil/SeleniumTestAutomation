package Cucumber.Rahul.LoginPage;

import Base.Drivers.DriverFactory;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.CheckoutPage;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.MainPageShop;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageCucumberSteps {

	WebDriver driver;
	WebDriverWait wait;
	LoginFormPage loginPageTest;


	@Given("Użytkownik uruchamia aplikację")
	public void setUp() {
		DriverFactory.initDriver("chrome", 15);
		driver = DriverFactory.getDriver();
		wait = DriverFactory.getWait();
		driver.get("https://rahulshettyacademy.com/locatorspractice/");
	}

	@When("Użytkownik wpisuje {string} oraz {string} w pola i loguje się do aplikacji")
	public void logIn(String login, String password)  {
		loginPageTest = new LoginFormPage();
		loginPageTest.elementShouldBeVisible(loginPageTest.formBdy)
				.verifyElementText(loginPageTest.submitBtn, "SIGN IN")
				.logInTo(login, password)
				.selectTermsAndConditions()
				.verifyTermsAreSelected()
				.clickSignIn();
	}

	@Then("Użytkownik widzi poprawny komunikat o zalogowaniu się a następnie wylogowuje się")
	public void logInValidation() {
		loginPageTest.verifySuccessLoginInfo()
				.shouldHaveSuccessMessageColor()
				.logoutVerification();
	}

	@After
	public void tearDown(){
		driver.quit();
	}
}