package POM.WebTest.RahulAcademy.Tests;

import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class LocatorsFormLoginTests extends BaseTest {





    @Test
    public void testSuccessfullyLogin() throws Exception {
        assertTrue(loginPageTest.isPresent(loginPageTest.formBdy));
        assertThat(loginPageTest.verifyElementText(loginPageTest.submitBtn, "Sign in")).isTrue();
        loginPageTest.logInTo().verifyTermsAreSelected().clickSignIn();
        loginPageTest

//        String successInfoText =  loginPageTest.getElementText(loginPageTest.successInfo);
//        log.info("Komunikat po zalogowaniu brzmi: {}", successInfoText);
//        String colorInfo = actions.find(rahulShettyStrong).getCss("color");
//        log.info("Kolor elementu ma następującą wartość: {}", colorInfo);
//        if(!colorInfo.equals("rgba(255, 75, 43, 1)")){
//            log.error("Błędna kolorystyka elementu, ma ona wartość: {}", colorInfo);
//        }
//        actions.find(logoutBtn).click();
//        actions.find(formBdy).isVisible();
    }

    @Test
    public void testFailedLogin()  {
        //Błędny login
        loginPageTest.logInTo("Login", "Haslo");
        loginPageTest.clickSignIn();
        loginPageTest.verifyElementVisibility(loginPageTest.errorLogMsg);
        loginPageTest.verifyElementText(loginPageTest.errorLogMsg, "* Incorrect username or password");
    }

    @Test
    public void testVisitUsButton()  {
        loginPageTest.verifyVisitUsBtn();
    }






}
