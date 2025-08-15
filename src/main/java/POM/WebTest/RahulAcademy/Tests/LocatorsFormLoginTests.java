package POM.WebTest.RahulAcademy.Tests;

import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class LocatorsFormLoginTests extends BaseTest {


    @Test
    public void testSuccessfullyLogin() throws Exception {
        loginPageTest.elementShouldBeVisible(loginPageTest.formBdy)
                .verifyElementText(loginPageTest.submitBtn, "SIGN IN")
                .logInTo()
                .selectTermsAndConditions()
                .verifyTermsAreSelected()
                .clickSignIn()
                .verifySuccessLoginInfo()
                .shouldHaveSuccessMessageColor()
                .logoutVerification();
    }

    @Test
    public void testFailedLogin()  {
        //Błędny login
        loginPageTest.elementShouldBeVisible(loginPageTest.formBdy)
        .verifyElementText(loginPageTest.submitBtn, "SIGN IN")
        .logInTo("Login", "Haslo").clickSignIn().shouldSeeErrorLoginMsg();
    }

    @Test
    public void testVisitUsButton()  {
        loginPageTest.useBtnVisitUs();
    }






}
