package POM.WebTest.BoniGarcia.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

public class DialogBoxesPage extends AbstractPage {


    public DialogBoxesPage(WebDriver driver, WebDriverWait wait, Logger log) {
        super(driver, wait, log);
        PageFactory.initElements(this.driver, this);
    }

    //Elementy na stronie
    @FindBy(id = "my-alert")
    WebElement LAUNCH_ALERT_BTN;

    @FindBy(id = "my-confirm")
    WebElement LAUNCH_CONFIRM_BTN;

    @FindBy(id = "my-prompt")
    WebElement PROMPT_ALERT_BTN;

    @FindBy(id = "my-modal")
    WebElement MODAL_ALERT_BTN;

    @FindBy(id = "confirm-text")
    WebElement CONFIRM_TEXT_PARAGRAPH;

    @FindBy(id = "prompt-text")
    WebElement PROMPT_TEXT_PARAGRAPH;

    @FindBy(id = "modal-text")
    WebElement MODAL_TEXT_PARAGRAPH;

    @FindBy(id = "example-modal")
    WebElement MODAL_ALERT_BODY;

    @FindBy(css = "button.btn-primary")
    WebElement MODAL_ALERT_SAVE_BTN;

    @FindBy(css = "button.btn-secondary")
    WebElement MODAL_ALERT_CANCEL_BTN;

    //Metody testowe
    public void launchAlertAndChoseBtn(WebElement btn){
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }

    public void launchAlert(){
        launchAlertAndChoseBtn(LAUNCH_ALERT_BTN);
    }
    public void launchConfirm(){
        launchAlertAndChoseBtn(LAUNCH_CONFIRM_BTN);
    }
    public void launchPrompt(){
        launchAlertAndChoseBtn(PROMPT_ALERT_BTN);
    }
    public void launchModal(){
        launchAlertAndChoseBtn(MODAL_ALERT_BTN);
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
        return wait.until(ExpectedConditions.visibilityOf(PROMPT_TEXT_PARAGRAPH)).getText();
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
        if(MODAL_ALERT_BODY.isDisplayed()){
            modalBodyText = MODAL_ALERT_BODY.findElement(By.cssSelector("div.modal-body")).getText();
        } else {
            launchModal();
            wait.until(ExpectedConditions.visibilityOf(MODAL_ALERT_BODY));
            modalBodyText = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.modal-body"))).getText();
        }
        if (saveOrClose.equalsIgnoreCase("save")){
            MODAL_ALERT_SAVE_BTN.click();
        } else if (saveOrClose.equalsIgnoreCase("close")){
            MODAL_ALERT_CANCEL_BTN.click();
        } else {
            throw  new RuntimeException("Błędna komenda do wykonania w oknie modalnym");
        }
        return modalBodyText;
    }

    public String getTextFromModal(){
        return wait.until(ExpectedConditions.visibilityOf(MODAL_TEXT_PARAGRAPH)).getText();
    }

    public String getTextFromConfirmParagraph(){
        return wait.until(ExpectedConditions.visibilityOf(CONFIRM_TEXT_PARAGRAPH)).getText();
    }

}
