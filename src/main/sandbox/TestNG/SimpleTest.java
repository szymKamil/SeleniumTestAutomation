package TestNG;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({AllureListenerTest.class})
public class SimpleTest {
	private String parameter;

	public SimpleTest(String parameter) {
		this.parameter = parameter;
	}

/*	@Test
	@Description("Sprawdzanie uruchomienia testu")
	@Epic("Funkcjonalność działania javy")
	@Feature("Logowanie")
	@Story("Sprawdzam działanie aplikajci")
	@Severity(SeverityLevel.BLOCKER)
	public void testMethod() {
		System.out.println("Test z parametrem: " + parameter);
	}*/
}