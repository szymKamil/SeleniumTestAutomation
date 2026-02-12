package Selenium.RahulAcademy;


import POM.WebTest.RahulAcademy.Pages.BaseTest.BaseTest;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import org.testng.annotations.Test;


public class LocatorsFormLoginTests extends BaseTest {

    LoginFormPage loginPageTest;

    @Test(invocationCount = 1, singleThreaded = true, successPercentage = 70)
    public void testSuccessfullyLogin() throws Exception {
        loginPageTest = new LoginFormPage();
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
        loginPageTest = new LoginFormPage();
        loginPageTest.elementShouldBeVisible(loginPageTest.formBdy)
        .verifyElementText(loginPageTest.submitBtn, "SIGN IN")
        .logInTo("Login", "Haslo").clickSignIn().shouldSeeErrorLoginMsg();
    }

    @Test
    public void testVisitUsButton()  {
        loginPageTest = new LoginFormPage();
        loginPageTest.useBtnVisitUs();
    }






}
