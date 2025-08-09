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
    private JavascriptExecutor js;
    Faker faker;

    public DataTypesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        PageFactory.initElements(driver, this);
        faker = new Faker();
    }

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



    public void insertDataToForm(Optional<String> firstName, Optional<String> lastName, Optional<String> address, Optional<String> zipCode,
                                 Optional<String> city, Optional<String> country, Optional<String> email, Optional<PhoneNumber> phone, Optional<Job> job,
                                 Optional<Company> company ){
        wait.until(ExpectedConditions.visibilityOf(inputFirstName)).sendKeys(firstName.get());
        assertThat(inputFirstName.getDomProperty("value")).isEqualTo(firstName.get());

        wait.until(ExpectedConditions.visibilityOf(inputLastName)).sendKeys(lastName.get());
        assertThat(inputLastName.getDomProperty("value")).isEqualTo(lastName.get());

        wait.until(ExpectedConditions.visibilityOf(inputAdress)).sendKeys(address.get());
        assertThat(inputAdress.getDomProperty("value")).isEqualTo(address.get());

        wait.until(ExpectedConditions.visibilityOf(inputZipCode)).sendKeys(zipCode.get());
        assertThat(inputZipCode.getDomProperty("value")).isEqualTo(zipCode.get());

        wait.until(ExpectedConditions.visibilityOf(inputCity)).sendKeys(city.get());
        assertThat(inputCity.getDomProperty("value")).isEqualTo(city.get());

        wait.until(ExpectedConditions.visibilityOf(inputCountry)).sendKeys(country.get());
        assertThat(inputCountry.getDomProperty("value")).isEqualTo(country.get());

        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email.get());
        assertThat(inputEmail.getDomProperty("value")).isEqualTo(email.get());

        wait.until(ExpectedConditions.visibilityOf(inputPhone)).sendKeys(phone.get().cellPhone());
//        assertThat(inputPhone.getDomProperty("value")).isEqualTo(phone.get().cellPhone());

        wait.until(ExpectedConditions.visibilityOf(inputJob)).sendKeys(job.get().position());
//        assertThat(inputJob.getDomProperty("value")).isEqualTo(job.get().position());

        wait.until(ExpectedConditions.visibilityOf(inputCompany)).sendKeys(company.get().bs());
//        assertThat(inputCompany.getDomProperty("value")).isEqualTo(company.get().bs());
    }

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
        wait.until(ExpectedConditions.invisibilityOf(submitBnt));
        wait.until(ExpectedConditions.attributeContains(inputFirstName, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputLastName, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputAdress, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputZipCode, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputCity, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputCountry, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputEmail, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputPhone, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputJob, "class", "alert-success"));
        wait.until(ExpectedConditions.attributeContains(inputCompany, "class", "alert-success"));

    }




}
