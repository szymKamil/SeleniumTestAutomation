package POM.WebTest.RahulAcademy.Tests;

import POM.WebTest.RahulAcademy.Pages.ShopTest.ShopPage;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.Test;

public class ShopPageTests extends BaseTest {



    @Test
    public void loginFormVerification(){
        pageShopLoginForm.insertLogin().insertPassword().choseRadio("Admin").selectForms("Consultant")
                .selectTermsAndConditions()
                .clickSignIn();

    }


    @Test
    public void verifyShopIsOnline(){
        shopPage.logInToAppAs("Admin", "Student").verifyShopIsOnline().verifySlider();
    }




}
