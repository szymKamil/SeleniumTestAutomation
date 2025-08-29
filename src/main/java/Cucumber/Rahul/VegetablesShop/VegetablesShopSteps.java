package Cucumber.Rahul.VegetablesShop;

import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import POM.WebTest.RahulAcademy.Pages.VegetablesShop.MainPageShop;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class VegetablesShopSteps {

	WebDriver driver;
	WebDriverWait wait;
	LoginFormPage loginPageTest;
	MainPageShop shopPage;

	@Given("Użytkownik uruchamia aplikację sklepu ważywnego")
	public void setUp() {
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		shopPage = new MainPageShop(driver, wait);
	}

	@When("Użytkownik korzysta z wyszukiwarki wpisując do niej {string}")
	public void searchVerification(String product){
		shopPage.searchForProduct(product);
	}

	@Then("Tylko ten jeden produkt jest widoczny na liście")
	public void numOfProdsVerification(){
		shopPage.numOfProdVerification();

	}

	@After
	public void tearDown(){
		driver.quit();
	}



}
