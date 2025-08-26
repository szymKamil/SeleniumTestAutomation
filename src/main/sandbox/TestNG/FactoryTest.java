package TestNG;

import io.qameta.allure.*;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class FactoryTest {

	private final String parameter;

	public FactoryTest(String parameter) {
		this.parameter = parameter;
	}

	@Test
	@Description("Sprawdzanie uruchomienia testu z parametrem")
	@Epic("Funkcjonalność działania javy")
	@Feature("Logowanie")
	@Story("Sprawdzam działanie aplikacji z parametrami")
	@Severity(SeverityLevel.BLOCKER)
	public void testMethod() {
		Allure.step("➡️ Uruchamiam test z parametrem: " + parameter);
		System.out.println("Test z parametrem: " + parameter);
	}

	@Factory
	public static Object[] factoryMethod() {
		return new Object[]{
				new FactoryTest("Parametr 1"),
				new FactoryTest("Parametr 2"),
				new FactoryTest("Parametr 3")
		};
	}
}


