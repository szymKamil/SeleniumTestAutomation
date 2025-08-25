package TestNG;

import org.testng.annotations.Test;

public class SimpleTest {
	private String parameter;

	public SimpleTest(String parameter) {
		this.parameter = parameter;
	}

	@Test
	public void testMethod() {
		System.out.println("Test z parametrem: " + parameter);
	}
}