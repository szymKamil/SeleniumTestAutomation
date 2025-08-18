package POM.WebTest.BoniGarcia.Pages;

import net.datafaker.Faker;
import net.datafaker.providers.base.Company;
import net.datafaker.providers.base.Job;
import net.datafaker.providers.base.PhoneNumber;
import net.datafaker.sequence.FakeCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

public class DataTypesPage {


    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    Faker faker;

    public DataTypesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        faker = new Faker();
    }

    //Elementy strony
    @FindBy(xpath = "//*[@name='first-name' or @id='first-name']")
    WebElement inputFirstName;

    @FindBy(xpath = "//*[@name='last-name' or @id='last-name']")
    WebElement inputLastName;

    @FindBy(xpath = "//*[@name='address' or @id='address']")
    WebElement inputAdress;

    @FindBy(xpath = "//*[@name='zip-code' or @id='zip-code']")
    WebElement inputZipCode;

    @FindBy(xpath = "//*[@name='city' or @id='city']")
    WebElement inputCity;

    @FindBy(xpath = "//*[@name='country' or @id='country']")
    WebElement inputCountry;

    @FindBy(xpath = "//*[@name='e-mail' or @id='e-mail']")
    WebElement inputEmail;

    @FindBy(xpath = "//*[@name='phone' or @id='phone']")
    WebElement inputPhone;

    @FindBy(xpath = "//*[@name='job-position' or @id='job-position']")
    WebElement inputJob;

    @FindBy(xpath = "//*[@name='company' or @id='company']")
    WebElement inputCompany;

    @FindBy(css = "button[type=submit]")
    WebElement submitBnt;


    //Metody testowe
    //Pobieramy wartości z metody, umieszczamy je w polach na stronie i weryfikujemy, czy zostały one poprawnie wpisane
    public void insertDataToForm(Optional<String> firstName, Optional<String> lastName, Optional<String> address, Optional<String> zipCode,
                                 Optional<String> city, Optional<String> country, Optional<String> email, Optional<PhoneNumber> phone, Optional<Job> job,
                                 Optional<Company> company ){
        wait.until(ExpectedConditions.visibilityOf(inputFirstName)).sendKeys(firstName.orElse("Jan"));
        assertThat(inputFirstName.getDomProperty("value")).isEqualTo(firstName.get());

        wait.until(ExpectedConditions.visibilityOf(inputLastName)).sendKeys(lastName.orElse("Nowak"));
        assertThat(inputLastName.getDomProperty("value")).isEqualTo(lastName.get());

        wait.until(ExpectedConditions.visibilityOf(inputAdress)).sendKeys(address.orElse("ul. Zawilcowa 22/2"));
        assertThat(inputAdress.getDomProperty("value")).isEqualTo(address.get());

        wait.until(ExpectedConditions.visibilityOf(inputZipCode)).sendKeys(zipCode.orElse("00-001"));
        assertThat(inputZipCode.getDomProperty("value")).isEqualTo(zipCode.get());

        wait.until(ExpectedConditions.visibilityOf(inputCity)).sendKeys(city.orElse("Warszawa"));
        assertThat(inputCity.getDomProperty("value")).isEqualTo(city.get());

        wait.until(ExpectedConditions.visibilityOf(inputCountry)).sendKeys(country.orElse("Polska"));
        assertThat(inputCountry.getDomProperty("value")).isEqualTo(country.get());

        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email.orElse("test_12345@google.com"));
        assertThat(inputEmail.getDomProperty("value")).isEqualTo(email.get());

        wait.until(ExpectedConditions.visibilityOf(inputPhone)).sendKeys(phone.isPresent() ? phone.get().cellPhone() : "654-342-646");

        wait.until(ExpectedConditions.visibilityOf(inputJob)).sendKeys(job.isPresent() ? job.get().position() : "księgowy");

        wait.until(ExpectedConditions.visibilityOf(inputCompany)).sendKeys((company.isPresent()) ? company.get().bs() : "Januszex sp. z oo.");
    }

    //Metoda domyślna
    public void insertDataToForm(){
        insertDataToForm(Optional.ofNullable(faker.name().firstName()),
                Optional.of(faker.name().lastName()), Optional.of(faker.address().fullAddress()), Optional.of(faker.address().zipCode()),
                Optional.of(faker.address().city()), Optional.of(faker.address().country()), Optional.of(faker.internet().emailAddress()),
                        Optional.of(faker.phoneNumber()), Optional.of(faker.job()), Optional.of(faker.company()));
    }

    public void submitForm(){
        wait.until(ExpectedConditions.elementToBeClickable(submitBnt)).click();
    }

    public void verifySuccessForm(){
        //Weryfikacja elementów po wypełnieniu formularza
        final String attribute = "class";
        final String value = "alert-success";

        wait.until(ExpectedConditions.invisibilityOf(submitBnt));
        wait.until(ExpectedConditions.attributeContains(inputFirstName, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputLastName, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputAdress, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputZipCode, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputCity, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputCountry, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputEmail, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputPhone, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputJob, attribute, value));
        wait.until(ExpectedConditions.attributeContains(inputCompany, attribute, value));

    }




}
