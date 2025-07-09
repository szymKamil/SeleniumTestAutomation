package POM.WebTest.BoniGarcia.Tests;



import POM.WebTest.BoniGarcia.Pages.Navigation;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class MainTest extends BaseTest {

    @Test
    public void mainPageTestElementsVerification(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka.
         */
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: " + currentUrl);
        }
        assertThat(driver.findElement(ap.img).isDisplayed()).isTrue();
        assertThat(driver.findElement(ap.mainHeader).getText()).isEqualTo(ap.pageTitle);
        assertThat(driver.findElements(mainPage.containers).size()).isEqualTo(6);
        assertThat(driver.findElement(mainPage.lead).isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.lead).getText()).isEqualTo(ap.leadText);
        assertThat(driver.findElement(mainPage.copySpan).getText()).contains(ap.copyrights);
    }

    @Test(priority = 2, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void webFormTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie i przesłanie formularza.
         */
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Web form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://bonigarcia.dev/selenium-webdriver-java/web-form.html")) {
            log.info("Adres URL jest poprawny.");
        } else {
            log.error("Niepoprawny adres URL: {}", currentUrl);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        assertThat(driver.findElement(ap.img)
                .isDisplayed()).isTrue();
        assertThat(driver.findElement(mainPage.copySpan)
                .getText()).contains(ap.copyrights);
        assertThat(driver.findElement(By.linkText("Return to index"))
                .isDisplayed()).isTrue();
        String testString = "Tekst przykładowy";
        webForm.fillTextInput(testString);
        String value = webForm.textInput.getAttribute("value");
        if (value.equals(testString)) {
            log.info("Tekst poprawnie wpisany w pole.");
        } else {
            log.error("Tekst niepoprawny, wpisano: '{}'", value);
        }
        String testPassword = "Haslo123_456";
        webForm.fillPasswordInput(testPassword);
        String passwordValue = webForm.passwordInput.getAttribute("value");
        if (passwordValue.equals(testPassword)) {
            log.info("Hasło poprawnie wpisane w pole.");
        } else {
            log.error("Hasło niepoprawne, wpisano: '{}'", passwordValue);
        }
        webForm.fillTextAreaInput(randomGeneratedText.get());
        String randomTextInput = webForm.textArea.getAttribute("value");
        log.info("Wpisano w pole tekstowe: {}", randomTextInput);
        webForm.selectElementOnDropdownList("Two");
        log.info("W pierwszej liście wybrano: {}", webForm.select.getFirstSelectedOption().getText());
        webForm.selectElementOnDataList(DropdownOptions.OPTION_B);






//        webForm.fillForm(
//                new Color(65, 45, 34, 2).asHex(),
//                LocalDate.of(2025, 12, 12));

    }

    @Test(priority = 2, dependsOnMethods ={"mainPageTestElementsVerification"})
    public void navigationPageTest(){
        /***
         * Test ma na celu uruchomienie przeglądarki, przejście do głównej strony, weryfikację adresu URL oraz tekstu nagłówka, a następnie wypełnienie i przesłanie formularza.
         */
        driver.get(mainPage.boniGarciaMainURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ap.mainHeader));
        mainPage.goToSubPage("Navigation");
        navigationPage.verifyNavigationPage();
        navigationPage.navigationExampleTest();



    }


}
