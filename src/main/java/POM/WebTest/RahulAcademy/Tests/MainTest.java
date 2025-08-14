package POM.WebTest.RahulAcademy.Tests;

import Base.BaseActionsAndUtils.PageLoadedVerification;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MainTest extends BaseTest{


    private static final Logger log = LoggerFactory.getLogger(MainTest.class);
    private final String pageURL = "https://rahulshettyacademy.com/locatorspractice/";


    @Test
    public void loginTest() throws Exception {
        driver.get(pageURL);
        assertThat(loginPageTest.formBody.isDisplayed()).isTrue();
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
    public void failedLogin() throws Exception {
        //Błędny login
        driver.get(pageURL);
        loginPageTest.insertLogInTo("Login", "Haslo");
        loginPageTest.clickSignIn();
        wait.until(ExpectedConditions.visibilityOf(loginPageTest.errorLoginMsg));
        wait.until(ExpectedConditions.textToBePresentInElement(loginPageTest.errorLoginMsg, "* Incorrect username or password"));
    }

    @Test
    public void visitUsTes() throws Exception {
        //Weryfikacja otwarcia strony po kliknięciu Visit us
        driver.get(pageURL);
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
