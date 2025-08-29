package POM.WebTest.RahulAcademy.DataProviders;

import org.testng.annotations.DataProvider;

public class DataProviderLoginFormTest {


    @DataProvider(name = "logInShopData")
    public Object[][] loginData(){
        return new Object[][]{
                {"Admin", "Student"},
                {"Admin", "Teacher"},
                {"Admin", "Consultant"},
                {"User", "Student"},
                {"User", "Teacher"},
                {"User", "Consultant"},
        };
    }
}
