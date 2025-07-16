package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
import net.datafaker.providers.base.TimeAndDate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class WebForm {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;
    public Select select;
    JavascriptExecutor js;

    private MainPage mainPage;
    private AbstractPage ap;

    public WebForm(WebDriver driver, WebDriverWait wait, Logger log) {
        this.driver = driver;
        this.wait = wait;
        this.log = log;
        mainPage = new MainPage(driver, wait, log);
        ap = new AbstractPage(driver, wait, log);
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(xpath = "//h1[@class='display-6']")
    public WebElement mainHeader;

    @FindBy(xpath = "//label[contains(., 'Text input')]/input")
    public WebElement textInput;

    @FindBy(xpath = "//label[contains(., 'Password')]/input")
    public WebElement passwordInput;

    @FindBy(xpath = "//label[contains(., 'Textarea')]/textarea")
    public WebElement textArea;

    @FindBy(name = "my-select")
    public WebElement selectElementList;

    @FindBy(name = "my-datalist")
    public WebElement dataList;

    @FindBy(name = "my-file")
    public WebElement fileField;

    @FindBy(id = "my-check-1")
    public WebElement checkbox1;

    @FindBy(id = "my-check-2")
    public WebElement checkbox2;

    @FindBy(id = "my-radio-1")
    public WebElement radio1;

    @FindBy(id = "my-radio-2")
    public WebElement radio2;

    @FindBy(name = "my-colors")
    public WebElement colorElement;

    @FindBy(name = "my-date")
    public WebElement dateField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement submitButton;

    @FindBy(xpath = "//h1[@class='display-6' and text()='Form submitted']")
    public WebElement h1SubmitFormConfirmation;


    public void fillTextInput(String inputText){
        wait.until(ExpectedConditions.visibilityOf(textInput)).sendKeys(inputText.toString());
    }

    public void fillPasswordInput(String inputText){
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(inputText.toString());
    }

    public void fillTextAreaInput(String inputText){
        wait.until(ExpectedConditions.visibilityOf(textArea)).sendKeys(inputText.toString());
    }

    public void selectElementOnDropdownList(String selectDropdownText){
        select = new Select(selectElementList);
        wait.until(ExpectedConditions.visibilityOf(selectElementList)).click();
        select.selectByVisibleText(selectDropdownText);
    }

    public void selectElementOnDataList(DropdownOptions dropdownOption){
        String dropdownElementText = getDatalistOption(dropdownOption);
        dataList.sendKeys(dropdownElementText);
    }

    public String getDatalistOption(DropdownOptions option) {
        String xpath = "//datalist/option[contains(@value, '" + option.getValue() + "')]";
        return driver.findElement(By.xpath(xpath)).getAttribute("value");
    }

    public void uploadFile(String path){
        Path filePath = Path.of(path);
        fileField.sendKeys(filePath.toString());
    }


    public void checkboxSelector(int checkboxNumToSelect){
        WebElement checkbox = switch (checkboxNumToSelect) {
            case 1 -> checkbox1;
            case 2 -> checkbox2;
            default -> throw new IllegalArgumentException("Checkbox number invalid");
        };
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void radioSelector(int radioNumToSelect){
        WebElement radioBtn = switch (radioNumToSelect) {
            case 1 -> radio1;
            case 2 -> radio2;
            default -> throw new IllegalArgumentException("Checkbox number invalid");
        };
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }
    }

    public void colorPicker(String hexColor){
        js.executeScript("arguments[0].setAttribute('value', '%s')".formatted(hexColor), colorElement);
    }

    public void dateSetter(LocalDate dataTestowa){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatedDate = dataTestowa.format(formatter);
        dateField.sendKeys(formatedDate);
    }


}
