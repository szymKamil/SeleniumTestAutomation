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

public class NotificationPage {


    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Actions actions;
    DevTools devTools;
    JavascriptExecutor js;

    public NotificationPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        devTools = ((HasDevTools) driver).getDevTools();
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "notify-me")
    WebElement notifyMeBtn;

    public void sendMeNotification(){
        notifyMeBtn.click();
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
