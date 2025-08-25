package POM.WebTest.RahulAcademy.Listener;



import static Base.Utils.Screenshot.takeScreenshot;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class WebDriverListener implements org.openqa.selenium.support.events.WebDriverListener {


    private static final Logger log = LoggerFactory.getLogger(WebDriverListener.class);


    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        log.error("Wystąpił błąd w teście, dotyczący {}, użyto metody {}. Komunikat z błędem: {}", target, method.getName(), e.toString());
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        log.info("Wykonuję screenshot");
        takeScreenshot(driver);
    }

    @Override
    public void beforeClick(WebElement element) {
        log.info("Klikam w element: {}", element.getTagName());
    }

    @Override
    public void beforeSendKeys(Alert alert, String text) {
        log.info("Wpisuję tekst {}", text);
    }
}
