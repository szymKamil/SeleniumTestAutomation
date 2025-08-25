package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DialogBoxesPage extends AbstractPage {


    public DialogBoxesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "my-alert")
    WebElement launchAlertBtn;

    @FindBy(id = "my-confirm")
    WebElement launchConfirmBtn;

    @FindBy(id = "my-prompt")
    WebElement promptAlertBtn;

    @FindBy(id = "my-modal")
    WebElement modalAlertBtn;

    @FindBy(id = "confirm-text")
    WebElement confirmTextParagraph;

    @FindBy(id = "prompt-text")
    WebElement promptTextParagraph;

    @FindBy(id = "modal-text")
    WebElement modalTextParagraph;

    @FindBy(id = "example-modal")
    WebElement modalAlertBody;

    @FindBy(css = "button.btn-primary")
    WebElement modalAlertSaveBtn;

    @FindBy(css = "button.btn-secondary")
    WebElement modalAlertCancelBtn;

    //Metody testowe
    public void launchAlertAndChoseBtn(WebElement btn){
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }

    public void launchAlert(){
        launchAlertAndChoseBtn(launchAlertBtn);
    }
    public void launchConfirm(){
        launchAlertAndChoseBtn(launchConfirmBtn);
    }
    public void launchPrompt(){
        launchAlertAndChoseBtn(promptAlertBtn);
    }
    public void launchModal(){
        launchAlertAndChoseBtn(modalAlertBtn);
    }

    public String getTextFromAlert(){
        boolean alertPresent;
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alertPresent = true;
        } catch (TimeoutException e) {
            alertPresent = false;
        }
        if (!alertPresent){
            launchAlert();
            wait.until(ExpectedConditions.alertIsPresent());
        }
        String alertText =  driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return alertText;

    }

    public String getTextFromConfirm(String btnToClickInAlert){
        boolean alertPresent;
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alertPresent = true;
        } catch (TimeoutException e) {
            alertPresent = false;
        }
        if (!alertPresent){
            launchConfirm();
            wait.until(ExpectedConditions.alertIsPresent());
        }
        String alertText =  driver.switchTo().alert().getText();
        if (btnToClickInAlert.equalsIgnoreCase("accept") || btnToClickInAlert.equalsIgnoreCase("OK")){
            driver.switchTo().alert().accept();
        } else {
            driver.switchTo().alert().dismiss();
        }
        return alertText;
    }

    public String getTextFromPrompt(){
        return wait.until(ExpectedConditions.visibilityOf(promptTextParagraph)).getText();
    }

    public void setTextToPromptAlert(String promptAlertInput){
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            launchPrompt();
            wait.until(ExpectedConditions.alertIsPresent());
        }
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(promptAlertInput);
        alert.accept();
    }


    public String handleModalWindow(String saveOrClose){
        String modalBodyText;
        if(modalAlertBody.isDisplayed()){
            modalBodyText = modalAlertBody.findElement(By.cssSelector("div.modal-body")).getText();
        } else {
            launchModal();
            wait.until(ExpectedConditions.visibilityOf(modalAlertBody));
            modalBodyText = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.modal-body"))).getText();
        }
        if (saveOrClose.equalsIgnoreCase("save")){
            modalAlertSaveBtn.click();
        } else if (saveOrClose.equalsIgnoreCase("close")){
            modalAlertCancelBtn.click();
        } else {
            throw  new RuntimeException("Błędna komenda do wykonania w oknie modalnym");
        }
        return modalBodyText;
    }

    public String getTextFromModal(){
        return wait.until(ExpectedConditions.visibilityOf(modalTextParagraph)).getText();
    }

    public String getTextFromConfirmParagraph(){
        return wait.until(ExpectedConditions.visibilityOf(confirmTextParagraph)).getText();
    }

}
