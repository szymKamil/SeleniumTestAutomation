package POM.WebTest.BoniGarcia.Pages;

import Base.Drivers.DriverFactory;
import Base.Utils.GenerateRandomText;
import Base.Utils.ParseWord;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class WebForm extends AbstractPage {


    public static final String TEST_STRING = "Tekst przykładowy";
    public static final String TEST_PASSWORD = "Haslo_123_456";
    static final String TEST_RANDOM_TEXT = GenerateRandomText.randomGeneratedText(88);
    static final String VALUE = "value";
    static final String CONFIRMATION_TEXT = "Form submitted";
    private static Select select;

    //Elementy na stronie
    @FindBy(xpath = "//h1[@class='display-6']")
    public WebElement mainHeader;

    @FindBy(id = "my-text-id")
    WebElement textInputEle;

    @FindBy(name = "my-password")
    WebElement passwordInput;

    @FindBy(xpath = "//label[contains(., 'Textarea')]/textarea")
    WebElement textAreaField;

    @FindBy(name = "my-disabled")
    WebElement disabledField;

    @FindBy(name = "my-readonly")
    WebElement readOnlyField;

    @FindBy(name = "my-select")
    WebElement dropdownSelectElement;

    @FindBy(name = "my-datalist")
    WebElement dataListElement;

    @FindBy(name = "my-file")
    WebElement fileField;

    @FindBy(id = "my-check-1")
    WebElement checkbox1;

    @FindBy(id = "my-check-2")
    WebElement checkbox2;

    @FindBy(id = "my-radio-1")
    WebElement radio1;

    @FindBy(id = "my-radio-2")
    WebElement radio2;

    @FindBy(name = "my-colors")
    WebElement colorElementPicker;

    @FindBy(name = "my-date")
    WebElement dateField;

    //TEST DATA
    @FindBy(name = "my-range")
    WebElement rangeSlider;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitBtn;

    @FindBy(xpath = "//h1[@class='display-6' and text()='Form submitted']")
    WebElement formSubmittedConfirmation;

    JavascriptExecutor js;

    public WebForm(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
        js = (JavascriptExecutor) this.driver;
    }

    //Metody testowe
    public void fillTextInput() {
        wait.until(ExpectedConditions.visibilityOf(textInputEle))
                .sendKeys(TEST_STRING);
        wait.until(ExpectedConditions.attributeToBe(textInputEle, VALUE, TEST_STRING));
        log.info("Login został  poprawnie wpisany.");
    }

    public void fillPasswordInput() {
        wait.until(ExpectedConditions.visibilityOf(passwordInput))
                .sendKeys(TEST_PASSWORD);
        wait.until(ExpectedConditions.attributeToBe(textInputEle, VALUE, TEST_STRING));
        log.info("Hasło zostało poprawnie wpisane.");
    }

    public void fillTextAreaInput() {
        wait.until(ExpectedConditions.visibilityOf(textAreaField))
                .sendKeys(TEST_RANDOM_TEXT);
        wait.until(ExpectedConditions.attributeToBe(textAreaField, VALUE, TEST_RANDOM_TEXT));
        log.info("Uzupełniony został tekst w polu Textarea.");
    }

    public void verifyDisabledFields() {
        wait.until(ExpectedConditions.domAttributeToBe(disabledField, "placeholder", "Disabled input"));
        wait.until(ExpectedConditions.domAttributeToBe(readOnlyField, VALUE, "Readonly input"));
        log.info("Nieaktywne przyciski są poprawnie wyłączne.");
    }


    public void selectElementOnDropdownList(String selectDropdownText) {
        select = new Select(dropdownSelectElement);
        wait.until(ExpectedConditions.visibilityOf(dropdownSelectElement))
                .click();
        select.selectByVisibleText(selectDropdownText);
        wait.until(ExpectedConditions.attributeToBe(dropdownSelectElement, VALUE, ParseWord.parseWord(selectDropdownText)
                .toString()));
        log.info("Wybrany został select w Dropdown List {}.", selectDropdownText);
    }

    public void selectElementOnDataList(DropdownOptions dropdownOption) {
        String dropdownElementText = getDataListOption(dropdownOption);
        dataListElement.sendKeys(dropdownElementText);
        wait.until(ExpectedConditions.attributeToBe(dataListElement, VALUE, dropdownElementText));
        log.info("Wybrany został następujący select w Data List {}.", dropdownElementText);
    }

    public String getDataListOption(DropdownOptions option) {
        String xpath = "//datalist/option[contains(@value, '" + option.getValue() + "')]";
        return driver.findElement(By.xpath(xpath))
                .getAttribute(VALUE);
    }

    public void uploadFile(String file) {
        File searchedFile;
        WebDriver driver = DriverFactory.getDriver();
        WebDriver rawDriver = driver;
        if (driver instanceof WrapsDriver) {
            rawDriver = ((WrapsDriver) driver).getWrappedDriver();
        }
        if (rawDriver instanceof RemoteWebDriver && System.getProperty("LocalTest").equals("false")) {
            System.out.println("Ustawiam LocalFileDetector na RemoteWebDriver");
            ((RemoteWebDriver) rawDriver).setFileDetector(new LocalFileDetector());
            searchedFile = new LocalFileDetector().getLocalFile(file).getAbsoluteFile();
            log.info("Ustawiam LocalFileDetector na RemoteWebDriver");
        } else {
            System.out.println("Sterownik NIE jest RemoteWebDriver. Typ: " + rawDriver.getClass().getName());
            log.warn("Sterownik NIE jest RemoteWebDriver. Typ: {}", rawDriver.getClass().getName());
            LocalFileDetector localFileDetector = new LocalFileDetector();
            searchedFile = localFileDetector.getLocalFile(file).getAbsoluteFile();
        }

        log.info("Dołączam plik: {}", file);
        fileField.sendKeys(searchedFile.toString());
        log.info("Plik został poprawnie przesłany.");

    }


    public void checkboxSelector(int checkboxNumToSelect) {
        List<WebElement> checkboxesList = switch (checkboxNumToSelect) {
            case 1 -> List.of(checkbox1, checkbox2);
            case 2 -> List.of(checkbox2, checkbox1);
            default -> throw new IllegalArgumentException("Błędny numer checkboxa");
        };
        for (WebElement checkBox : checkboxesList) {
            checkBox.click();
        }
        log.info("Zmieniono zaznaczenie checkboxów.");
    }

    public void checkboxSelector() {
        checkboxSelector(1);
    }

    public void radioSelector(int radioNumToSelect) {
        WebElement radioBtn = switch (radioNumToSelect) {
            case 1 -> radio1;
            case 2 -> radio2;
            default -> throw new IllegalArgumentException("Błędny numer radiobutton");
        };
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }
        log.info("Test radio button zakończony.");
    }

    public void colorPicker(String hexColor) {
        String initialColor = colorElementPicker.getDomProperty("value");
        js.executeScript("arguments[0].setAttribute('value', '%s')".formatted(hexColor), colorElementPicker);
        String modifyColor = colorElementPicker.getDomProperty("value");
        log.info("Początkową wartością koloru było {}, po zmianie było to {}", initialColor, modifyColor);
    }

    public void dateSetter(LocalDate dataTestowa) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatedDate = dataTestowa.format(formatter);
        dateField.sendKeys(formatedDate);
        dateField.sendKeys(Keys.ENTER);
        log.info("Wpisana została data: {}", dateField.getAttribute(VALUE));
    }

    public void changeRange() {
        wait.until(ExpectedConditions.visibilityOf(rangeSlider))
                .sendKeys(Keys.ARROW_LEFT);
    }


    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn))
                .click();
        wait.until(ExpectedConditions.textToBePresentInElement(formSubmittedConfirmation, CONFIRMATION_TEXT));
    }


}
