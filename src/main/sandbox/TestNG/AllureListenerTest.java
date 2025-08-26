package TestNG;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public class AllureListenerTest implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		Allure.step("➡️ Start testu: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Allure.step("✅ Test zakończony sukcesem: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Allure.step("❌ Test nieudany: " + result.getMethod().getMethodName());

		if (result.getThrowable() != null) {
			Allure.addAttachment("Exception", result.getThrowable().toString());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Allure.step("⚠️ Test pominięty: " + result.getMethod().getMethodName());
	}

	// UWAGA: tutaj już nie używaj Allure.step()
	@Override
	public void onStart(ITestContext context) {
		System.out.println("=== START suite: " + context.getName() + " ===");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("=== KONIEC suite: " + context.getName() + " ===");
	}
}
