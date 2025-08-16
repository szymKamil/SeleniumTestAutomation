package POM.WebTest.RahulAcademy.Pages.SignInFormTest;

import POM.WebTest.RahulAcademy.Pages.ShopTest.LoginForm;
import POM.WebTest.RahulAcademy.TestActionUtils.WebElementActions;

public class ShopPage extends WebElementActions {

    LoginForm loginForm;



    public ShopPage logInToAppAs(String userType, String userRole){
        loginForm.insertLogin().insertPassword().choseRadio(userType).selectForms(userRole).selectTermsAndConditions().clickSignIn();
        return this;
    }




}
