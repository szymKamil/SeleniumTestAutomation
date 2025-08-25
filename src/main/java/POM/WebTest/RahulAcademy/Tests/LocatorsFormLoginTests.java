package POM.WebTest.RahulAcademy.Tests;


import Base.BaseTest.DriverFactoryV1;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import org.testng.annotations.Test;


public class LocatorsFormLoginTests extends BaseTest {

    LoginFormPage loginPageTest;

    @Test(invocationCount = 5, singleThreaded = true, successPercentage = 70)
    public void testSuccessfullyLogin() throws Exception {
        loginPageTest = new LoginFormPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
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
        loginPageTest = new LoginFormPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        loginPageTest.elementShouldBeVisible(loginPageTest.formBdy)
        .verifyElementText(loginPageTest.submitBtn, "SIGN IN")
        .logInTo("Login", "Haslo").clickSignIn().shouldSeeErrorLoginMsg();
    }

    @Test
    public void testVisitUsButton()  {
        loginPageTest = new LoginFormPage(DriverFactoryV1.getDriver(), DriverFactoryV1.getWait());
        loginPageTest.useBtnVisitUs();
    }






}
