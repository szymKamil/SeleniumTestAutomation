package Cucumber.Rahul.LoginPage;

import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import io.cucumber.java.After;
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
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/locatorspractice/");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@When("Użytkownik wpisuje {string} oraz {string} w pola i loguje się do aplikacji")
	public void logIn(String login, String password) throws Exception {
		loginPageTest = new LoginFormPage(driver, wait);
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
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}