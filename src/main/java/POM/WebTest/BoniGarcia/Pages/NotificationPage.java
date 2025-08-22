package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationPage extends AbstractPage{


    Actions actions;
    DevTools devTools;
    JavascriptExecutor js;

    public NotificationPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(driver, this);
        actions = new Actions(this.driver);
        devTools = ((HasDevTools) this.driver).getDevTools();
        js = (JavascriptExecutor) this.driver;
    }

    //Elementy na stronie
    @FindBy(id = "notify-me")
    WebElement NOTIFY_ME_BTN;

    //Metody testowe
    public void sendMeNotification(){
        NOTIFY_ME_BTN.click();
    }


    public void createAndSendNotification(String title, String notificationBody){
        Object result = js.executeAsyncScript("""
                const callback = arguments[arguments.length - 1];
                new Notification("%s", { body: "%s" });
                setTimeout(() => callback("Notification sent!"), 4000);
                """.formatted(title, notificationBody));
        assertThat(result).isEqualTo("Notification sent!");

    }


}
