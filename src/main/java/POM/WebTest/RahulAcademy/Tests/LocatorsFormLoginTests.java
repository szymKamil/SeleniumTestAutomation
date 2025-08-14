package POM.WebTest.RahulAcademy.Tests;

import Base.Utils.JavaScriptUtils;
import Base.Utils.Utils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class LocatorsFormLoginTests extends BaseTest {


    private static final Logger log = LoggerFactory.getLogger(LocatorsFormLoginTests.class);

    @Test
    public void testSuccessfullyLogin() throws Exception {
        assertTrue(loginPageTest.formBody.isDisplayed());
        assertThat(loginPageTest.signInHeader.getText()).isEqualTo("Sign in");
        loginPageTest.insertLogInTo();
        assertThat(loginPageTest.checkboxRememberUserName.isSelected()).isFalse();
        if (!loginPageTest.checkboxAgreeTermsAndPolicy.isSelected()) {
            loginPageTest.checkboxAgreeTermsAndPolicy.click();
        }
        assertThat(loginPageTest.checkboxAgreeTermsAndPolicy.isSelected()).isTrue();
        loginPageTest.clickSignIn();
        loginPageTest.verifySuccessfulLogin();
    }

    @Test
    public void testFailedLogin() throws Exception {
        //Błędny login
        loginPageTest.insertLogInTo("Login", "Haslo");
        loginPageTest.clickSignIn();
        wait.until(ExpectedConditions.visibilityOf(loginPageTest.errorLoginMsg));
        wait.until(ExpectedConditions.textToBePresentInElement(loginPageTest.errorLoginMsg, "* Incorrect username or password"));
    }

    @Test
    public void testVisitUsButton() throws Exception {
        //Weryfikacja otwarcia strony po kliknięciu Visit us
        String currentHandle = driver.getWindowHandle();
        loginPageTest.visitUsClick();
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
