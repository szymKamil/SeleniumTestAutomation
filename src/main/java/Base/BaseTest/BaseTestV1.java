package Base.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTestV1 {

    WebDriver webDriver;
    WebDriverWait wait;



    public BaseTestV1(String browser) {
        this.webDriver = WebDriverManager.getInstance(browser).getWebDriver();
    }







}
