package POM.WebTest.BoniGarcia.Tests;



import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.util.Optional;


public class MainTest extends BaseTest {

    @Test
    public void mainPageTestElementsVerification(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.
         */
        driver.get(mainPage.boniGarciaMainURL);
        mainPage.verifyMainPage();
    }

    @Test(priority = 2, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void webFormTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie i przesłanie formularza.
         */
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.mainHeader));
        mainPage.goToSubPage("Web form");
        webForm.verifyFormPage();
        webForm.fillForm(Optional.of("Tekst przykładowy"), Optional.of("Haslo123_456"), randomGeneratedText.get(), "Two",
                POM.WebTest.BoniGarcia.Utils.DropdownOptions.OPTION_B,
                new Color(65, 45, 34, 2).asHex(),
                LocalDate.of(2025, 12, 12));

    }

}
