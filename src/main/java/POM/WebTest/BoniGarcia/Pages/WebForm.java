package POM.WebTest.BoniGarcia.Pages;

import POM.WebTest.BoniGarcia.Utils.DropdownOptions;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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



    public void fillForm(String selectDropdownText, DropdownOptions dropdownOption, String hexColor, LocalDate dataTestowa) {



        String dropdownElementText = getDatalistOption(dropdownOption);
        dataList.sendKeys(dropdownElementText);
        log.info("Na liście dropdown wybrano: {}", dataList.getAttribute("value"));

        assertThat(fileField.isDisplayed()).isTrue();
        Path fvPath = Path.of("D:\\Programowanie\\Nauka\\SeleniumTestAutomation\\SeleniumTestAutomation\\src\\main\\resources\\f-vat_2011.pdf");
        fileField.sendKeys(fvPath.toString());
        log.info("Na dodano plik testowy: {}", fileField.getAttribute("value"));

        assertThat(checkbox1.isDisplayed()).isTrue();
        if (!checkbox1.isSelected()){
            checkbox1.click();
        }
        assertThat(checkbox1.isSelected()).isTrue();
        assertThat(checkbox2.isDisplayed()).isTrue();
        if (checkbox2.isSelected()){
            checkbox2.click();
        }
        assertThat(checkbox2.isSelected()).isFalse();

        assertThat(radio1.isDisplayed()).isTrue();
        if (!radio1.isSelected()){
            radio1.click();
        }
        assertThat(radio1.isSelected()).isTrue();
        assertThat(radio2.isSelected()).isFalse();

        assertThat(colorElement.isDisplayed()).isTrue();
        String initialColor = colorElement.getDomProperty("value");
        js.executeScript("arguments[0].setAttribute('value', '%s')".formatted(hexColor), colorElement);
        String modifyColor = colorElement.getDomProperty("value");
        log.info("Początkową wartością koloru było {}, po zmianie było to {}", initialColor, modifyColor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatedDate = dataTestowa.format(formatter);
        dateField.sendKeys(formatedDate);
        log.info("Wpisana została data: {}", dataTestowa);

        assertThat(submitButton.isDisplayed()).isTrue();
        submitButton.click();
        wait.until(ExpectedConditions.visibilityOf(h1SubmitFormConfirmation));
        assertThat(driver.findElement(By.className("lead")).getText()).isEqualTo("Received!");
    }


    public String getDatalistOption(DropdownOptions option) {
        String xpath = "//datalist/option[contains(@value, '" + option.getValue() + "')]";
        return driver.findElement(By.xpath(xpath)).getAttribute("value");
    }

}
