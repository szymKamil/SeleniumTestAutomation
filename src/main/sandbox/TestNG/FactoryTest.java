package TestNG;

import org.testng.annotations.Factory;
public class FactoryTest {

	@Factory
	public Object[] createTestInstances() {
		System.out.println("Tworzenie instancji testowych...");
		return new Object[] {
				new SimpleTest("Parametr 1"),
				new SimpleTest("Parametr 2"),
				new SimpleTest("Parametr 3")
		};
	}
}


