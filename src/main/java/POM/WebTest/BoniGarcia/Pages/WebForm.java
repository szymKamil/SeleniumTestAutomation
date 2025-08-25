package POM.WebTest.BoniGarcia.Pages;

import Base.Utils.GenerateRandomText;
import Base.Utils.ParseWord;
import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class WebForm extends AbstractPage {


    public final String TEST_STRING = "Tekst przykładowy";
    public final String TEST_PASSWORD = "Haslo_123_456";
    final String TEST_RANDOM_TEXT = GenerateRandomText.randomGeneratedText(88);
    final String VALUE = "value";
    final String CONFIRMATION_TEXT = "Form submitted";
    public Select select;
    //Elementy na stronie
    @FindBy(xpath = "//h1[@class='display-6']")
    public WebElement mainHeader;
    @FindBy(id = "my-text-id")
    public WebElement TEXT_INPUT_ELE;
    @FindBy(name = "my-password")
    public WebElement passwordInput;
    @FindBy(xpath = "//label[contains(., 'Textarea')]/textarea")
    public WebElement TEXT_AREA_FIELD;
    @FindBy(name = "my-disabled")
    public WebElement DISABLED_FIELD;
    @FindBy(name = "my-readonly")
    public WebElement READ_ONLY_FIELD;
    @FindBy(name = "my-select")
    public WebElement DROPDOWN_SELECT_ELEMENT;
    @FindBy(name = "my-datalist")
    public WebElement DATA_LIST_ELEMENT;
    @FindBy(name = "my-file")
    public WebElement FILE_FIELD;
    @FindBy(id = "my-check-1")
    public WebElement CHECKBOX_1;
    @FindBy(id = "my-check-2")
    public WebElement CHECKBOX_2;
    @FindBy(id = "my-radio-1")
    public WebElement RADIO_1;
    @FindBy(id = "my-radio-2")
    public WebElement RADIO_2;
    @FindBy(name = "my-colors")
    public WebElement COLOR_ELEMENT_PICKER;
    @FindBy(name = "my-date")
    public WebElement DATE_FIELD;


    //TEST DATA
    @FindBy(name = "my-range")
    public WebElement RANGE_SLIDER;
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement SUBMIT_BTN;
    @FindBy(xpath = "//h1[@class='display-6' and text()='Form submitted']")
    public WebElement FORM_SUBMITTED_CONFIRMATION;
    JavascriptExecutor js;

    public WebForm(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.driver, this);
        js = (JavascriptExecutor) this.driver;
    }

    //Metody testowe
    public void fillTextInput() {
        wait.until(ExpectedConditions.visibilityOf(TEXT_INPUT_ELE))
                .sendKeys(TEST_STRING);
        wait.until(ExpectedConditions.attributeToBe(TEXT_INPUT_ELE, VALUE, TEST_STRING));
        log.info("Login został  poprawnie wpisany.");
    }

    public void fillPasswordInput() {
        wait.until(ExpectedConditions.visibilityOf(passwordInput))
                .sendKeys(TEST_PASSWORD);
        wait.until(ExpectedConditions.attributeToBe(TEXT_INPUT_ELE, VALUE, TEST_STRING));
        log.info("Hasło zostało poprawnie wpisane.");
    }

    public void fillTextAreaInput() {
        wait.until(ExpectedConditions.visibilityOf(TEXT_AREA_FIELD))
                .sendKeys(TEST_RANDOM_TEXT);
        wait.until(ExpectedConditions.attributeToBe(TEXT_AREA_FIELD, VALUE, TEST_RANDOM_TEXT));
        log.info("Uzupełniony został tekst w polu Textarea.");
    }

    public void verifyDisabledFields() {
        wait.until(ExpectedConditions.domAttributeToBe(DISABLED_FIELD, "placeholder", "Disabled input"));
        wait.until(ExpectedConditions.domAttributeToBe(READ_ONLY_FIELD, VALUE, "Readonly input"));
        log.info("Nieaktywne przyciski są poprawnie wyłączne.");
    }


    public void selectElementOnDropdownList(String selectDropdownText) {
        select = new Select(DROPDOWN_SELECT_ELEMENT);
        wait.until(ExpectedConditions.visibilityOf(DROPDOWN_SELECT_ELEMENT))
                .click();
        select.selectByVisibleText(selectDropdownText);
        wait.until(ExpectedConditions.attributeToBe(DROPDOWN_SELECT_ELEMENT, VALUE, ParseWord.parseWord(selectDropdownText)
                .toString()));
        log.info("Wybrany został select w Dropdown List {}.", selectDropdownText);
    }

    public void selectElementOnDataList(DropdownOptions dropdownOption) {
        String dropdownElementText = getDataListOption(dropdownOption);
        DATA_LIST_ELEMENT.sendKeys(dropdownElementText);
        wait.until(ExpectedConditions.attributeToBe(DATA_LIST_ELEMENT, VALUE, dropdownElementText));
        log.info("Wybrany został następujący select w Data List {}.", dropdownElementText);
    }

    public String getDataListOption(DropdownOptions option) {
        String xpath = "//datalist/option[contains(@value, '" + option.getValue() + "')]";
        return driver.findElement(By.xpath(xpath))
                .getAttribute(VALUE);
    }

    public void uploadFile(String path) {
        FILE_FIELD.sendKeys(path);
        log.info("Plik został poprawnie przesłany.");
    }


    public void checkboxSelector(int checkboxNumToSelect) {
        List<WebElement> checkboxesList = switch (checkboxNumToSelect) {
            case 1 -> List.of(CHECKBOX_1, CHECKBOX_2);
            case 2 -> List.of(CHECKBOX_2, CHECKBOX_1);
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
            case 1 -> RADIO_1;
            case 2 -> RADIO_2;
            default -> throw new IllegalArgumentException("Błędny numer radiobutton");
        };
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }
        log.info("Test radio button zakończony.");
    }

    public void colorPicker(String hexColor) {
        String initialColor = COLOR_ELEMENT_PICKER.getDomProperty("value");
        js.executeScript("arguments[0].setAttribute('value', '%s')".formatted(hexColor), COLOR_ELEMENT_PICKER);
        String modifyColor = COLOR_ELEMENT_PICKER.getDomProperty("value");
        log.info("Początkową wartością koloru było {}, po zmianie było to {}", initialColor, modifyColor);
    }

    public void dateSetter(LocalDate dataTestowa) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatedDate = dataTestowa.format(formatter);
        DATE_FIELD.sendKeys(formatedDate);
        DATE_FIELD.sendKeys(Keys.ENTER);
        log.info("Wpisana została data: {}", DATE_FIELD.getAttribute(VALUE));
    }

    public void changeRange() {
        wait.until(ExpectedConditions.visibilityOf(RANGE_SLIDER))
                .sendKeys(Keys.ARROW_LEFT);
    }


    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BTN))
                .click();
        wait.until(ExpectedConditions.textToBePresentInElement(FORM_SUBMITTED_CONFIRMATION, CONFIRMATION_TEXT));
    }


}
