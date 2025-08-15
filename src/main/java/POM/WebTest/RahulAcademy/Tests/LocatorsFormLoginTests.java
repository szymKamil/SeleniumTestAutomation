package POM.WebTest.RahulAcademy.Tests;

import Base.Utils.JavaScriptUtils;
import Base.Utils.Utils;
import POM.WebTest.RahulAcademy.Pages.LoginTest.LoginPageTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class LocatorsFormLoginTests extends LoginPageTest {


    private static final Logger log = LoggerFactory.getLogger(LocatorsFormLoginTests.class);

    public LocatorsFormLoginTests(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
    }

    @Test
    public void testSuccessfullyLogin() throws Exception {
        assertTrue(verifyElementVisibility(formBdy));
        assertThat(verifyElementText(submitBtn, "Sign in")).isTrue();
        insertLogInTo();
        assertThat(verifyTermsAreSelected()).isFalse();
        if (!verifyTermsAreSelected()) {
            selectTermsAndConditions();
        }
        assertThat(verifyTermsAreSelected()).isTrue();
        clickSignIn();
        verifySuccessfulLogin();
    }

    @Test
    public void testFailedLogin()  {
        //Błędny login
        insertLogInTo("Login", "Haslo");
        clickSignIn();
        verifyElementVisibility(errorLogMsg);
        verifyElementText(errorLogMsg, "* Incorrect username or password");
    }

    @Test
    public void testVisitUsButton()  {
        //Weryfikacja otwarcia strony po kliknięciu Visit us
        String currentHandle = driver.getWindowHandle();
        visitUsClick();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        List windowHandels = driver.getWindowHandles().stream().toList();
        if(windowHandels.size() == 2){
            if (driver.getWindowHandle().equals(currentHandle)){
                driver.switchTo().window(windowHandels.get(1).toString());
            }
        }
        String url = driver.getCurrentUrl();
        assert url != null;
        assertThat(url.contains("https://rahulshettyacademy.com/")).isTrue();
        log.info("URL po przejściu do Visit Us to: {}", url);
    }






}
