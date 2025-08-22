package POM.WebTest.BoniGarcia.Pages;

import net.datafaker.Faker;
import net.datafaker.providers.base.Company;
import net.datafaker.providers.base.Job;
import net.datafaker.providers.base.PhoneNumber;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

public class DataTypesPage extends AbstractPage{



    Faker faker;

    public DataTypesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
        faker = new Faker();
    }

    //Elementy strony
    @FindBy(xpath = "//*[@name='first-name' or @id='first-name']")
    WebElement INPUT_FIRST_NAME;

    @FindBy(xpath = "//*[@name='last-name' or @id='last-name']")
    WebElement INPUT_LAST_NAME;

    @FindBy(xpath = "//*[@name='address' or @id='address']")
    WebElement INPUT_ADRESS;

    @FindBy(xpath = "//*[@name='zip-code' or @id='zip-code']")
    WebElement INPUT_ZIP_CODE;

    @FindBy(xpath = "//*[@name='city' or @id='city']")
    WebElement INPUT_CITY;

    @FindBy(xpath = "//*[@name='country' or @id='country']")
    WebElement INPUT_COUNTRY;

    @FindBy(xpath = "//*[@name='e-mail' or @id='e-mail']")
    WebElement INPUT_EMAIL;

    @FindBy(xpath = "//*[@name='phone' or @id='phone']")
    WebElement INPUT_PHONE;

    @FindBy(xpath = "//*[@name='job-position' or @id='job-position']")
    WebElement INPUT_JOB;

    @FindBy(xpath = "//*[@name='company' or @id='company']")
    WebElement INPUT_COMPANY;

    @FindBy(css = "button[type=submit]")
    WebElement SUBMIT_BTN;

    final String ATTRIBUTE = "class";
    final String VALUE = "alert-success";


    //Metody testowe
    //Pobieramy wartości z metody, umieszczamy je w polach na stronie i weryfikujemy, czy zostały one poprawnie wpisane
    public void insertDataToForm(Optional<String> firstName, Optional<String> lastName, Optional<String> address, Optional<String> zipCode,
                                 Optional<String> city, Optional<String> country, Optional<String> email, Optional<PhoneNumber> phone, Optional<Job> job,
                                 Optional<Company> company ){
        wait.until(ExpectedConditions.visibilityOf(INPUT_FIRST_NAME)).sendKeys(firstName.orElse("Jan"));
        assertThat(INPUT_FIRST_NAME.getDomProperty("value")).isEqualTo(firstName.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_LAST_NAME)).sendKeys(lastName.orElse("Nowak"));
        assertThat(INPUT_LAST_NAME.getDomProperty("value")).isEqualTo(lastName.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_ADRESS)).sendKeys(address.orElse("ul. Zawilcowa 22/2"));
        assertThat(INPUT_ADRESS.getDomProperty("value")).isEqualTo(address.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_ZIP_CODE)).sendKeys(zipCode.orElse("00-001"));
        assertThat(INPUT_ZIP_CODE.getDomProperty("value")).isEqualTo(zipCode.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_CITY)).sendKeys(city.orElse("Warszawa"));
        assertThat(INPUT_CITY.getDomProperty("value")).isEqualTo(city.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_COUNTRY)).sendKeys(country.orElse("Polska"));
        assertThat(INPUT_COUNTRY.getDomProperty("value")).isEqualTo(country.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_EMAIL)).sendKeys(email.orElse("test_12345@google.com"));
        assertThat(INPUT_EMAIL.getDomProperty("value")).isEqualTo(email.get());
        wait.until(ExpectedConditions.visibilityOf(INPUT_PHONE)).sendKeys(phone.isPresent() ? phone.get().cellPhone() : "654-342-646");
        wait.until(ExpectedConditions.visibilityOf(INPUT_JOB)).sendKeys(job.isPresent() ? job.get().position() : "księgowy");
        wait.until(ExpectedConditions.visibilityOf(INPUT_COMPANY)).sendKeys((company.isPresent()) ? company.get().bs() : "Januszex sp. z oo.");
    }

    //Metoda domyślna
    public void insertDataToForm(){
        insertDataToForm(Optional.ofNullable(faker.name().firstName()),
                Optional.of(faker.name().lastName()), Optional.of(faker.address().fullAddress()), Optional.of(faker.address().zipCode()),
                Optional.of(faker.address().city()), Optional.of(faker.address().country()), Optional.of(faker.internet().emailAddress()),
                        Optional.of(faker.phoneNumber()), Optional.of(faker.job()), Optional.of(faker.company()));
    }

    public void submitForm(){
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BTN)).click();
    }

    public void verifySuccessForm(){
        //Weryfikacja elementów po wypełnieniu formularza
        wait.until(ExpectedConditions.invisibilityOf(SUBMIT_BTN));
        wait.until(ExpectedConditions.attributeContains(INPUT_FIRST_NAME, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_LAST_NAME, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_ADRESS, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_ZIP_CODE, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_CITY, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_COUNTRY, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_EMAIL, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_PHONE, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_JOB, ATTRIBUTE, VALUE));
        wait.until(ExpectedConditions.attributeContains(INPUT_COMPANY, ATTRIBUTE, VALUE));

    }




}
