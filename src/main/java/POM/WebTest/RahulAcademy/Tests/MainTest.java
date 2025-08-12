package POM.WebTest.RahulAcademy.Tests;

import org.testng.annotations.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MainTest extends BaseTest{


    @Test
    public void loginTest() throws Exception {
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
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


}
