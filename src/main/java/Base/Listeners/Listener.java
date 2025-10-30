package Base.Listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Listener implements WebDriverListener {

	private void attach(WebDriver driver, String name){
		if (name != null && name.contains("findElement")) return;

		try {
			byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Allure.step(name, () -> Allure.getLifecycle()
					.addAttachment(
							"screenshot", "image/png", "png", new ByteArrayInputStream(png)
					));
		} catch (Exception ignore){}
	}

	private String safeLocator(WebElement e){
		try { return e.toString();} catch (Exception exception) {	return "<element>";	}
	}

	@Override
	public void beforeClick(WebElement element) {
		attach(((WrapsDriver) element).getWrappedDriver(), "beforeClick: " + safeLocator(element));
	}

	@Override
	public void afterClick(WebElement element) {
		attach(((WrapsDriver) element).getWrappedDriver(), "afterClick: " + safeLocator(element));
	}

	@Override
	public void beforeSendKeys(WebElement element, CharSequence... text) {
		attach(((WrapsDriver) element).getWrappedDriver(), "beforeSednKeys: " + safeLocator(element));
	}

	@Override
	public void afterSendKeys(WebElement element, CharSequence... text) {
		attach(((WrapsDriver) element).getWrappedDriver(), "afterSednKeys: " + safeLocator(element));
	}

	@Override
	public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
		WebDriver d = (target instanceof WebDriver) ?
				(WebDriver) target : (target instanceof WebElement) ?
				((WrapsDriver) target).getWrappedDriver() : null;
		if (d != null) attach(d, "Error in: " + method.getName());
	}
}
