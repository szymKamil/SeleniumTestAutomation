package Selenium.RahulAcademy;


import Base.Drivers.DriverFactory;
import POM.WebTest.RahulAcademy.Pages.SignInFormTest.LoginFormPage;
import Selenium.RahulAcademy.Base.BaseTest;
import org.testng.annotations.Test;


public class LocatorsFormLoginTests extends BaseTest {

    LoginFormPage loginPageTest;

    @Test(invocationCount = 5, singleThreaded = true, successPercentage = 70)
    public void testSuccessfullyLogin() throws Exception {
        loginPageTest = new LoginFormPage(DriverFactory.getDriver(), DriverFactory.getWait());
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
        loginPageTest = new LoginFormPage(DriverFactory.getDriver(), DriverFactory.getWait());
        loginPageTest.elementShouldBeVisible(loginPageTest.formBdy)
        .verifyElementText(loginPageTest.submitBtn, "SIGN IN")
        .logInTo("Login", "Haslo").clickSignIn().shouldSeeErrorLoginMsg();
    }

    @Test
    public void testVisitUsButton()  {
        loginPageTest = new LoginFormPage(DriverFactory.getDriver(), DriverFactory.getWait());
        loginPageTest.useBtnVisitUs();
    }






}
