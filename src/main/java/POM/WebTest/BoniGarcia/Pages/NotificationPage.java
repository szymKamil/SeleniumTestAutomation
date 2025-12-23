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

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationPage extends AbstractPage{


    Actions actions;
    //DevTools devTools;
    JavascriptExecutor js;

    public NotificationPage(WebDriver driver, WebDriverWait wait) {
        super();
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        //devTools = ((HasDevTools) driver).getDevTools();
        js = (JavascriptExecutor) driver;
    }

    //Elementy na stronie
    @FindBy(id = "notify-me")
    WebElement notifyMeBtn;

    //Metody testowe
    public void sendMeNotification(){
        notifyMeBtn.click();
    }


    public void createAndSendNotification(String title, String notificationBody){
        Object result = js.executeAsyncScript("""
                const callback = arguments[arguments.length - 1];
                if (typeof Notification === "undefined") {
                        callback("Notification API not available");
                        return;
                    }
                if (Notification.permission === "granted") {
                        new Notification("%s", { body: "%s" });
                        setTimeout(() => callback("Notification sent!"), 4000);
                    } else {
                        callback("Notification not granted");
                    }
                """.formatted(title, notificationBody));
        assertThat(result).isEqualTo("Notification sent!");
    }


}
